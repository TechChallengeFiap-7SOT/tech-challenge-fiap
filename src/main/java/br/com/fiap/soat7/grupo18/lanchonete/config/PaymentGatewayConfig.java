package br.com.fiap.soat7.grupo18.lanchonete.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.AbstractPagamentoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.MercadoPagoPagamentoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.MockPagamentoGateway;

@Configuration
public class PaymentGatewayConfig {


    @Bean
    @ConditionalOnProperty(name = "app.payment.gateway", havingValue = "mercadopago")
    public AbstractPagamentoGateway getMercadoPagoGateway(){
        return new MercadoPagoPagamentoGateway();
    }

    @Bean
    @ConditionalOnProperty(name = "app.payment.gateway", havingValue = "mock", matchIfMissing = true)
    public AbstractPagamentoGateway getMockGateway(){
        return new MockPagamentoGateway();
    }

}
