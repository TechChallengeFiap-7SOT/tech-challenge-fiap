package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.PedidoController;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.ClienteGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.ProdutoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.PedidoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ClienteUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ProdutoUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerResponseDto;

@Service
public class PedidoRestService {

    private final ClienteUseCase clienteUseCase;
    private final ProdutoUseCase produtoUseCase;
    private final PedidoController pedidoController;
    
    public PedidoRestService(@Qualifier("pedidoDatabaseRepository") PedidoDataRepository pedidoDataRepository,
                                @Qualifier("clienteDatabaseRepository") ClienteDataRepository clienteRepository,
                                @Qualifier("produtoDatabaseRepository") ProdutoDataRepository produtoRepository) {
        this.clienteUseCase = new ClienteUseCase(new ClienteGateway(clienteRepository));
        this.produtoUseCase = new ProdutoUseCase(new ProdutoGateway(produtoRepository));
        this.pedidoController = new PedidoController(pedidoDataRepository);
    }

    public PedidoHandlerResponseDto findById(String idPedido) {
        return pedidoController.findById(idPedido);
    }

    public PedidoHandlerResponseDto save(PedidoHandlerRequestDto pedidoDto) {
        return pedidoController.save(pedidoDto, produtoUseCase, clienteUseCase);
    }

    public void updateStatus(String idPedido, String novoStatusStr) {
        pedidoController.updateStatus(idPedido, novoStatusStr);
    }

    public void updateStatusPgto(String idPedido, String novoStatusPgtoStr) {
        pedidoController.updateStatusPgto(idPedido, novoStatusPgtoStr);
    }

    public StatusPgtoType getStatusPgto(String idPedido) {
        return pedidoController.getStatusPgto(idPedido);
    }

    public List<PedidoHandlerResponseDto> findAll() {
        return pedidoController.findAll();
    }

    public List<PedidoHandlerResponseDto> findNaoFinalizadosOuCancelados() {
        return pedidoController.findNaoFinalizadosOuCancelados();
    }

}
