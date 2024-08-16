package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.PedidoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter.PedidoPresenter;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.PedidoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ClienteUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.PedidoUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ProdutoUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.AbstractPagamentoGateway;

public class PedidoController {

    private final PedidoDataRepository pedidoRepository;
    private final PedidoUseCase pedidoUseCase;
    
    /**
     * 
     * @param pedidoRepository
     */
    public PedidoController(PedidoDataRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoUseCase = new PedidoUseCase(new PedidoGateway(this.pedidoRepository));
    }

    public PedidoHandlerResponseDto findById(String idPedido) {
        return PedidoPresenter.mapToDto(pedidoUseCase.findById(idPedido));
    }

    public PedidoHandlerResponseDto save(PedidoHandlerRequestDto pedidoDto, ProdutoUseCase produtoUseCase, ClienteUseCase clienteUseCase,
                                            AbstractPagamentoGateway pagamentoGateway) {
        return PedidoPresenter.mapToDto(pedidoUseCase.save(pedidoDto, produtoUseCase, clienteUseCase, pagamentoGateway));
    }

    public void updateStatus(String idPedido, String novoStatusStr) {
        pedidoUseCase.updateStatus(idPedido, novoStatusStr);
    }

    public void updateStatusPgto(String idPedido, String novoStatusPgtoStr) {
        pedidoUseCase.updateStatusPgto(idPedido, novoStatusPgtoStr, null);
    }

    public StatusPgtoType getStatusPgto(String idPedido) {
        return pedidoUseCase.getStatusPgto(idPedido);
    }

    public List<PedidoHandlerResponseDto> findAll() {
        return pedidoUseCase.findAll()
                    .stream()
                    .map(PedidoPresenter::mapToDto)
                    .toList();
    }

    public List<PedidoHandlerResponseDto> findNaoFinalizadosOuCancelados() {
        return pedidoUseCase.findNaoFinalizadosOuCancelados()
                    .stream()
                    .map(PedidoPresenter::mapToDto)
                    .toList();
    }



    

}
