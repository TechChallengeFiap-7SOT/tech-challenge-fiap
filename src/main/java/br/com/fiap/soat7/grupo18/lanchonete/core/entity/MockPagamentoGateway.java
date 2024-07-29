package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

public class MockPagamentoGateway extends AbstractPagamentoGateway<Map<String, Object>> {

    private StatusPgtoType statusPgto;

    private LocalDateTime dataHoraPgto;

    private String idTransacao;

    public MockPagamentoGateway(Map<String, Object> mapaRetornoGateway) {
        super(mapaRetornoGateway);
        //como é um mock, gera um número aleatório de 1 a 5, onde sendo gerado o valor 1, informa que o pgto foi recusado. Apenas para testes...
        final int random = new Random().nextInt(5)+1;
        statusPgto = random == 1 ? StatusPgtoType.RECUSADO : StatusPgtoType.APROVADO;
        dataHoraPgto = LocalDateTime.now();
        idTransacao = String.format("%02d_%s", random, UUID.randomUUID().toString());
    }

    @Override
    public StatusPgtoType getStatusPgto() {
        return statusPgto;
    }

    @Override
    public LocalDateTime getDataHoraPgto() {
        return dataHoraPgto;
    }

    @Override
    public String getIdTransacao() {
        return idTransacao;
    }

}
