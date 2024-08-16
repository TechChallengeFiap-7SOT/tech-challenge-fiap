package br.com.fiap.soat7.grupo18.lanchonete.core.usecase;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.NotFoundEntityException;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.AbstractPagamentoGateway;

public class PaymentWebhookUseCase {


    private final AbstractPagamentoGateway pagamentoGateway;

    public PaymentWebhookUseCase(AbstractPagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    public void efetuaPagamentoPedido(String idPedido, PedidoUseCase pedidoUseCase){
        var pedido = pedidoUseCase.findById(idPedido);
        if (pedido == null){
            throw new NotFoundEntityException("Pedido não encontrado com o ID informado: " + idPedido);
        }
        //TODO corrigir isso, pq não existem mais tais métodos no pgtoGateway
        pedidoUseCase.updateStatusPgto(idPedido, "pagamentoGateway.getStatusPgto().name()", "pagamentoGateway.getIdTransacao()");
    }

    

}
