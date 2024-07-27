package br.com.fiap.soat7.grupo18.lanchonete.core.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.PedidoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.ComboPedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.ItemPedidoProduto;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Pedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ComboPedidoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainUseCaseException;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.NotFoundEntityException;

public class PedidoUseCase {

    private static final Comparator<Pedido> DEFAULT_SORT_COMPARATOR = Comparator.comparingInt((Pedido p) -> p.getStatus().getFlowOrder())
                                                                            .reversed()
                                                                            .thenComparing(Pedido::getDataHora, Comparator.reverseOrder());

    private final PedidoGateway pedidoGateway;

    public PedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido findById(String idPedido) {
        return Optional.ofNullable(idPedido != null && !idPedido.isBlank() ? pedidoGateway.findById(idPedido) : null)
                        .orElseThrow(() -> new NotFoundEntityException("Pedido não localizado com o ID informado: " + idPedido));
    }

    public Pedido save(PedidoHandlerRequestDto pedidoDto, ProdutoUseCase produtoUseCase, ClienteUseCase clienteUseCase) {
        if (!Optional.ofNullable(pedidoDto).isPresent()){
            throw new DomainUseCaseException("Dados do pedido não enviados");
        }

        boolean cpfInformado = pedidoDto.getCpfCliente() != null && !pedidoDto.getCpfCliente().isBlank();
        Cliente cliente = cpfInformado ? clienteUseCase.findByCpfCliente(pedidoDto.getCpfCliente()) : null;
        if (cpfInformado && cliente == null){
            throw new DomainUseCaseException("Cliente não localizado");
        }

        if (pedidoDto.getProdutos() == null || pedidoDto.getProdutos().isEmpty()){
            throw new DomainUseCaseException("Nenhum item enviado no pedido");
        }

        List<ComboPedido> produtos = validaEMontaCombosPedido(pedidoDto, produtoUseCase);
        Pedido novoPedido = new Pedido(UUID.randomUUID().toString(), cliente, LocalDateTime.now(), StatusPedidoType.RECEBIDO.name(),
                                    StatusPgtoType.AGUARDANDO.name(), produtos);


        return pedidoGateway.save(novoPedido);
    }

    public void updateStatus(String idPedido, StatusPedidoType novoStatus) {
        if (novoStatus == null){
            throw new DomainUseCaseException("Novo status do pedido não informado");
        }

        var pedido = pedidoGateway.findById(idPedido);
        if (List.of(StatusPedidoType.FINALIZADO, StatusPedidoType.CANCELADO).contains(pedido.getStatus())){
            throw new DomainUseCaseException("Pedidos finalizados ou cancelados não podem ser alterados");
        }

        if (StatusPgtoType.APROVADO != pedido.getStatusPgto() && novoStatus.isPaymentRequired()){
            throw new DomainUseCaseException("Esse status exige que o pagamento do pedido esteja na situação " + StatusPgtoType.APROVADO.name());
        }

        //é possível retroceder, porém não pular etapas do pedido
        boolean atualizandoParaEtapaPosterior = novoStatus.getFlowOrder() > pedido.getStatus().getFlowOrder();
        if (!List.of(StatusPedidoType.CANCELADO, pedido.getProximoStatus()).contains(novoStatus) && atualizandoParaEtapaPosterior){
            final String statusFlowDiagram = Stream.of(StatusPedidoType.values())
                                                    .filter(s -> StatusPedidoType.CANCELADO != s)
                                                    .sorted(Comparator.comparingInt(StatusPedidoType::getFlowOrder))
                                                    .map(StatusPedidoType::name)
                                                    .collect(Collectors.joining(" > "));

            throw new DomainUseCaseException(String.format("Novo status inválido. É necessário seguir o fluxo correto do pedido: %s", statusFlowDiagram));
        }

        pedidoGateway.updateStatus(idPedido, novoStatus);

    }

    public void updateStatusPgto(String idPedido, StatusPgtoType novoStatusPgto) {
        if (novoStatusPgto == null){
            throw new DomainUseCaseException("Novo status de pagamento não informado");
        }

        var pedido = pedidoGateway.findById(idPedido);

        if (!List.of(StatusPgtoType.APROVADO, StatusPgtoType.RECUSADO).contains(novoStatusPgto)){
            throw new DomainUseCaseException(String.format("O status do pagamento só pode ser atualizado para %s ou %s",
                                                        StatusPgtoType.APROVADO.name(), StatusPgtoType.RECUSADO.name()));
        }

        if (List.of(StatusPedidoType.CANCELADO, StatusPedidoType.FINALIZADO).contains(pedido.getStatus())){
            throw new DomainUseCaseException("Não é possível atualizar o status de pagamento de pedidos cancelados ou finalizados");
        }

        //se o pgto foi recusado, o pedido deve voltar para 'RECEBIDO' caso ainda não esteja. Se aprovado, então atualiza o pedido para 'EM_PREPARACAO'
        boolean deveAtualizarStatusPedido = (StatusPgtoType.RECUSADO == novoStatusPgto && StatusPedidoType.RECEBIDO != pedido.getStatus())
                                                ||
                                            (StatusPgtoType.APROVADO == novoStatusPgto && StatusPedidoType.RECEBIDO == pedido.getStatus());

        var novoStatusPedido = StatusPgtoType.RECUSADO == novoStatusPgto ? StatusPedidoType.RECEBIDO : StatusPedidoType.EM_PREPARACAO;

        pedidoGateway.updateStatusPgto(idPedido, novoStatusPgto);
        if (deveAtualizarStatusPedido){
            updateStatus(idPedido, novoStatusPedido);
        }

    }

    public StatusPgtoType getStatusPgto(String idPedido) {
        return Optional.ofNullable(idPedido != null && !idPedido.isBlank() ? pedidoGateway.getStatusPgto(idPedido) : null)
                        .orElseThrow(() -> new NotFoundEntityException("Pedido não encontrado com o ID informado: " + idPedido));

    }

    public List<Pedido> findAll() {
        return pedidoGateway.findAll().stream()
                    .sorted(DEFAULT_SORT_COMPARATOR)
                    .toList();
    }

    public List<Pedido> findNaoFinalizadosOuCancelados() {
        return pedidoGateway.findNaoFinalizadosOuCancelados().stream()
                    .sorted(DEFAULT_SORT_COMPARATOR)
                    .toList();
    }

    private List<ComboPedido> validaEMontaCombosPedido(PedidoHandlerRequestDto pedidoDto, ProdutoUseCase produtoUseCase) {
        int comboNum = 0;
        List<ComboPedido> listaComboPedido = new ArrayList<>();
        for (ComboPedidoHandlerRequestDto prodDto : pedidoDto.getProdutos()){
            Map<Categoria, ItemPedidoProduto> mapaCategoriaProduto = new HashMap<>();
            var idLanche = prodDto.getItemLanche() != null ? prodDto.getItemLanche().getIdProduto() : null;
            var idAcompanhamento = prodDto.getItemAcompanhamento() != null ? prodDto.getItemAcompanhamento().getIdProduto() : null;
            var idBebida = prodDto.getItemBebida() != null ? prodDto.getItemBebida().getIdProduto() : null;
            var idSobremesa = prodDto.getItemSobremesa() != null ? prodDto.getItemSobremesa().getIdProduto() : null;

            if (idLanche != null){
                var produto = produtoUseCase.findAtivoByIdProduto(idLanche);
                if (produto == null || !produto.isCategoriaLanche()){
                    throw new DomainUseCaseException(String.format("Produto ativo não localizado na categoria 'Lanche': %s", idLanche));
                }
                mapaCategoriaProduto.put(produto.getCategoria(), new ItemPedidoProduto(produto.getId(), produto.getPreco()));
            }

            if (idAcompanhamento != null){
                var produto = produtoUseCase.findAtivoByIdProduto(idAcompanhamento);
                if (produto == null || !produto.isCategoriaAcompanhamento()){
                    throw new DomainUseCaseException(String.format("Produto ativo não localizado na categoria 'Acompanhamento': %s", idAcompanhamento));
                }
                mapaCategoriaProduto.put(produto.getCategoria(), new ItemPedidoProduto(produto.getId(), produto.getPreco()));
            }

            if (idBebida != null){
                var produto = produtoUseCase.findAtivoByIdProduto(idBebida);
                if (produto == null || !produto.isCategoriaBebida()){
                    throw new DomainUseCaseException(String.format("Produto ativo não localizado na categoria 'Bebida': %s", idBebida));
                }
                mapaCategoriaProduto.put(produto.getCategoria(), new ItemPedidoProduto(produto.getId(), produto.getPreco()));
            }

            if (idSobremesa != null){
                var produto = produtoUseCase.findAtivoByIdProduto(idSobremesa);
                if (produto == null || !produto.isCategoriaSobremesa()){
                    throw new DomainUseCaseException(String.format("Produto ativo não localizado na categoria 'Sobremesa': %s", idSobremesa));
                }
                mapaCategoriaProduto.put(produto.getCategoria(), new ItemPedidoProduto(produto.getId(), produto.getPreco()));
            }
            listaComboPedido.add(new ComboPedido(mapaCategoriaProduto, ++comboNum));
        }
        return listaComboPedido;
    }

}
