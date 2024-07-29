package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class PaymentWebhookHandlerRequestDto {

    private JsonNode body;

    public PaymentWebhookHandlerRequestDto(JsonNode body) {
        this.body = body;
    }

    public JsonNode getBody() {
        return body;
    }

}
