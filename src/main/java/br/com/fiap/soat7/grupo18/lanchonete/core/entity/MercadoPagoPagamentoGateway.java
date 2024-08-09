package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.time.LocalDateTime;
import java.util.Map;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

/**
 * Classe 'ilustrativa' da possível integração com o mercado pago. Para os testes, utilizar a classe MockPagamentoGateway, afim de simular as transações
 */
public class MercadoPagoPagamentoGateway extends AbstractPagamentoGateway<String> {

    public MercadoPagoPagamentoGateway(String mapaRetornoGateway) {
        super(mapaRetornoGateway);
        throw new UnsupportedOperationException("Integração não implementada. Utilize o 'mock' para os testes");
    }

    @Override
    public StatusPgtoType getStatusPgto() {
        throw new UnsupportedOperationException("Unimplemented method 'getStatusPgto'");
    }

    @Override
    public LocalDateTime getDataHoraPgto() {
        throw new UnsupportedOperationException("Unimplemented method 'getDataHoraPgto'");
    }

    @Override
    public String getIdTransacao() {
        throw new UnsupportedOperationException("Unimplemented method 'getIdTransacao'");
    }

    @Override
    public String geraRequisicaoPgto(Map<String, Object> params) {
        // TODO prosseguir implementando a integração com a loja...
        throw new UnsupportedOperationException("Unimplemented method 'geraRequisicaoPgto'");
    }

}
