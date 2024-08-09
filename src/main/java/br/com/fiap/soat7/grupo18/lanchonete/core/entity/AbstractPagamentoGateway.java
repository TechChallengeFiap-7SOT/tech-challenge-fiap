package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.time.LocalDateTime;
import java.util.Map;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

public abstract class AbstractPagamentoGateway<T> {

    protected final T bodyRequestPgtoGateway;

    public AbstractPagamentoGateway(T mapaRetornoGateway) {
        this.bodyRequestPgtoGateway = mapaRetornoGateway;
    }

    public abstract StatusPgtoType getStatusPgto();

    public abstract LocalDateTime getDataHoraPgto();

    public abstract String getIdTransacao();

    public abstract String geraRequisicaoPgto(Map<String, Object> params);
    
}
