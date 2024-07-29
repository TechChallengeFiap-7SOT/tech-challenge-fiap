package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.PaymentWebhookController;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.PedidoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.AbstractPagamentoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.PedidoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.PedidoUseCase;

@Service
public class PaymentWebhookRestService {

    private final PedidoUseCase pedidoUseCase;
    
    public PaymentWebhookRestService(@Qualifier("pedidoDatabaseRepository") PedidoDataRepository pedidoDataRepository) {
        this.pedidoUseCase = new PedidoUseCase(new PedidoGateway(pedidoDataRepository));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void efetuaPagamentoPedido(String idPedido, AbstractPagamentoGateway<?> pagamentoGateway){
        new PaymentWebhookController(pagamentoGateway).efetuaPagamentoPedido(idPedido, pedidoUseCase);
    }

    

}
