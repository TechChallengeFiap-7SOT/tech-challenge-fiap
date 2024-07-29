package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

public class PedidoHandlerResponseDto {

    private String idPedido;

    private LocalDateTime dataHora;

    private BigDecimal valor;

    private StatusPedidoType status;

    private StatusPgtoType statusPgto;

    private ClienteHandlerResponseDto cliente;

    private List<ComboPedidoHandlerResponseDto> combos;

    /**
     * 
     * @param idPedido
     * @param dataHora
     * @param valor
     * @param status
     * @param statusPgto
     * @param cliente
     * @param combos
     */
    public PedidoHandlerResponseDto(String idPedido, LocalDateTime dataHora, BigDecimal valor, StatusPedidoType status,
            StatusPgtoType statusPgto, ClienteHandlerResponseDto cliente, List<ComboPedidoHandlerResponseDto> combos) {
        this.idPedido = idPedido;
        this.dataHora = dataHora;
        this.valor = valor;
        this.status = status;
        this.statusPgto = statusPgto;
        this.cliente = cliente;
        this.combos = combos != null ? List.copyOf(combos) : List.of();
    }

    public String getIdPedido() {
        return idPedido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public StatusPedidoType getStatus() {
        return status;
    }

    public StatusPgtoType getStatusPgto() {
        return statusPgto;
    }

    public ClienteHandlerResponseDto getCliente() {
        return cliente;
    }

    public List<ComboPedidoHandlerResponseDto> getCombos() {
        return combos;
    }

}
