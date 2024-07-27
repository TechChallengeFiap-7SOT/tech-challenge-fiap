package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.time.LocalDateTime;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Pagamento {

    private final String idExterno;
    private final LocalDateTime dataHora;
    private final StatusPgtoType status;
    
    /**
     * 
     * @param idExterno
     * @param dataHora
     * @param status
     */
    public Pagamento(String idExterno, LocalDateTime dataHora, StatusPgtoType status) {
        this.idExterno = idExterno;
        this.dataHora = dataHora;
        this.status = status;

        if (idExterno == null || idExterno.isBlank() || dataHora == null || status == null){
            throw new DomainEntityException("ID externo, dataHora e status são obrigatórios para criar o pagamento");
        }
    }

    /**
     * 
     * @param idExterno
     * @param dataHora
     */
    public Pagamento(String idExterno, LocalDateTime dataHora) {
        this(idExterno, dataHora, StatusPgtoType.AGUARDANDO);
    }

    /**
     * 
     * @param idExterno
     */
    public Pagamento(String idExterno) {
        this(idExterno, LocalDateTime.now(), StatusPgtoType.AGUARDANDO);
    }

    public String getIdExterno() {
        return idExterno;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusPgtoType getStatus() {
        return status;
    }
    
}
