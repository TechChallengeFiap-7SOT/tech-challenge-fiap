package br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

public class MockPagamentoGateway extends AbstractPagamentoGateway {

    private Random random = new Random();

    private StatusPgtoType statusPgto;

    private LocalDateTime dataHoraPgto;

    private String idTransacao;

    public StatusPgtoType getStatusPgto() {
        return statusPgto;
    }

    public LocalDateTime getDataHoraPgto() {
        return dataHoraPgto;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    @Override
    public String geraRequisicaoPgto(PagamentoOrder pgtoOrder) {
        //como é um mock, gera um número aleatório de 1 a 5, onde sendo gerado o valor 1, informa que o pgto foi recusado. Apenas para testes...
        final int randomInt = random.nextInt(5)+1;
        statusPgto = randomInt == 1 ? StatusPgtoType.RECUSADO : StatusPgtoType.APROVADO;
        dataHoraPgto = LocalDateTime.now();
        idTransacao = String.format("%02d_%s", randomInt, UUID.randomUUID().toString());
        return "ok";
    }

}
