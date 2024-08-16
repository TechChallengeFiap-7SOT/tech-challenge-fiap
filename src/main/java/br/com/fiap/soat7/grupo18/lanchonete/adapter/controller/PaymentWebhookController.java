package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.PaymentWebhookUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.PedidoUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.AbstractPagamentoGateway;

public class PaymentWebhookController {

    private final PaymentWebhookUseCase paymentUseCase;

    public PaymentWebhookController(AbstractPagamentoGateway pagamentoGateway) {
        this.paymentUseCase = new PaymentWebhookUseCase(pagamentoGateway);
    }

    public void efetuaPagamentoPedido(String idPedido, PedidoUseCase pedidoUseCase){
        paymentUseCase.efetuaPagamentoPedido(idPedido, pedidoUseCase);
    }

}
