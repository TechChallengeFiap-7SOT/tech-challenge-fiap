package br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                                            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
                                                            .appendOffset("+HH:MM", "+00:00")
                                                            .toFormatter();

    @Value("${app.payment.gateway.mercadopago.test-accesstoken}")
    private String testAccessToken;

    @Override
    public String geraRequisicaoPgto(PagamentoOrder pgtoOrder) {
        ZonedDateTime expirationDate = ZonedDateTime.now(ZoneId.of("UTC"))
                                                .plusMinutes(30L); // o pedido ficará pendente por 30 minutos até expirar (caso não pago).

        WebClient webClient = WebClient.builder().build();
        try{
            final var item = new MercadoPagoOrderItem();
            item.unitPrice = Double.valueOf(pgtoOrder.getTotalPedido().toString());
            item.totalAmount = Double.valueOf(pgtoOrder.getTotalPedido().toString());

            final var mapaRequest = Map.of(
                                        "description", pgtoOrder.getDescricaoPedido(),
                                        "external_reference", pgtoOrder.getIdPedido(),
                                        "items", List.of(item),
                                        "expiration_date", expirationDate.format(formatter),
                                        "notification_url", NOTIFICATION_URL,
                                        "title", pgtoOrder.getDescricaoPedido(),
                                        "total_amount", Double.valueOf(pgtoOrder.getTotalPedido().toString())
                                        );
            var apiResponse = webClient.post()
                                .uri(URI.create(URL_GENERATE_QR))
                                .header("Authorization", String.format("Bearer %s", testAccessToken))
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

    @SuppressWarnings("unused")// na verdade elas são usadas... é preciso enviar ao WS do mercado pago
    private static class MercadoPagoOrderItem{

        private String category = "marketplace";

        private String title = "Pedido lanchonete";

        private String description = "Pedido lanchonete";

        @JsonProperty("unit_price")
        private Double unitPrice;

        private int quantity = 1;

        @JsonProperty("unit_measure")
        private String unitMeasure = "unit";

        @JsonProperty("total_amount")
        private Double totalAmount;

        public String getCategory() {
            return category;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getUnitMeasure() {
            return unitMeasure;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

    }

}
