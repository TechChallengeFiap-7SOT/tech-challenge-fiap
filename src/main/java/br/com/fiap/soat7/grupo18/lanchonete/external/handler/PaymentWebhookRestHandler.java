package br.com.fiap.soat7.grupo18.lanchonete.external.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.fiap.soat7.grupo18.lanchonete.external.handler.service.PaymentWebhookRestService;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.AbstractPagamentoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.MercadoPagoPagamentoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway.MockPagamentoGateway;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lanchonete/v1/payment/webhook")
@Tag(name = "Webhook pagamentos", description = "Webhook para integração com interface de pagamentos")
public class PaymentWebhookRestHandler {

    private PaymentWebhookRestService paymentService;

    public PaymentWebhookRestHandler(PaymentWebhookRestService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{gateway}/{idPedido}")
    @Operation(description = "Recebe o retorno de um pagamento do gateway informado. Para o escopo da aplicação, informar sempre 'MOCK' no parâmetro 'gateway'")
    @ApiResponse(responseCode = "200", description = "Sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<String> handlePaymentWebhook(@RequestBody JsonNode notification, @PathVariable String gateway,
                                                                    @PathVariable String idPedido) {
        AbstractPagamentoGateway pagamentoGateway;
        switch (gateway.toUpperCase()) {
            case "MOCK":
                pagamentoGateway = new MockPagamentoGateway();
                break;

            case "MERCADOPAGO":
                pagamentoGateway = new MercadoPagoPagamentoGateway();
                break;
        
            default:
                return ResponseEntity.badRequest().body("Gateway de pagamento não reconhecido. Por momento informe 'MOCK' no path param");
        }

        paymentService.efetuaPagamentoPedido(idPedido, pagamentoGateway);
        //TODO implementar isso
        return ResponseEntity.ok().body("Status pgto: " + "pagamentoGateway.getStatusPgto()");
    }
}
