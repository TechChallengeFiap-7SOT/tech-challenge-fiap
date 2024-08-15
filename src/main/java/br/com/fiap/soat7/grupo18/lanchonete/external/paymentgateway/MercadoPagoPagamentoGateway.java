package br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.PagamentoGatewayException;
import reactor.core.publisher.Mono;


public class MercadoPagoPagamentoGateway extends AbstractPagamentoGateway {

    private static final String USER_ID = "116238642";//gerado previamente na configuração da API
    private static final String EXTERNAL_STORE_ID = "MATRIZ001"; //gerado previamente na configuração da API
    private static final String URL_GENERATE_QR = String.format("https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs",
                                                    USER_ID, EXTERNAL_STORE_ID);

    private static final String NOTIFICATION_URL = "https://www.yourserver.com/notifications";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public String geraRequisicaoPgto(PagamentoOrder pgtoOrder) {
        LocalDateTime expirationOrderDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                                                    .plusMinutes(30L); // o pedido ficará pendente por 30 minutos até expirar (caso não pago).

        WebClient webClient = WebClient.builder().build();
        try{
            final var mapaRequest = Map.of(
                                        "description", pgtoOrder.getDescricaoPedido(),
                                        "external_reference", pgtoOrder.getIdPedido(),
                                        "expiration_date", formatter.format(expirationOrderDate),
                                        "notification_url", NOTIFICATION_URL,
                                        "title", pgtoOrder.getDescricaoPedido(),
                                        "total_amount", pgtoOrder.getTotalPedido().toString()
                                        );
            var apiResponse = webClient.post()
                                .uri(URI.create(URL_GENERATE_QR))
                                .body(Mono.just(mapaRequest), Map.class)
                                .retrieve()
                                .bodyToMono(MercadoPagoApiResponse.class)
                                .block();
            return apiResponse.qrData;
        }catch(WebClientResponseException ex){
            throw new PagamentoGatewayException("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
        }
    }

    private static class MercadoPagoApiResponse{
        
        @JsonProperty("qr_data")
        private String qrData;

        @JsonProperty("in_store_order_id")
        private String storeOrderId;
    }

}
