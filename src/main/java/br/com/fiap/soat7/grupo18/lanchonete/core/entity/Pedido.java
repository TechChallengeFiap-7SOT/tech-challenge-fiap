package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Pedido {

    private final String id;

    private final Cliente cliente;

    private final LocalDateTime dataHora;

    private final BigDecimal valor;

    private final StatusPedidoType status;

    private final StatusPgtoType statusPgto;

    private final List<ComboPedido> produtos;

    /**
     * 
     * @param id
     * @param cliente
     * @param dataHora
     * @param status
     * @param statusPgto
     * @param produtos
     */
    public Pedido(String id, Cliente cliente, LocalDateTime dataHora, String status, String statusPgto, List<ComboPedido> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.status = StatusPedidoType.parseByName(status);
        this.statusPgto = StatusPgtoType.parseByName(statusPgto);
        this.produtos = Collections.unmodifiableList(produtos != null ? produtos : Collections.emptyList())
                            .stream()
                            .map(prod -> new ComboPedido(id, prod.getMapaCombo(), prod.getComboNum()))
                            .collect(Collectors.toUnmodifiableList());
        this.valor = this.produtos.stream()
                            .map(ComboPedido::getPreco)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (this.id == null || this.id.isBlank()){
            throw new DomainEntityException("ID do pedido é obrigatório");
        }

         if (this.cliente != null && this.cliente.getCpf() == null){
            throw new DomainEntityException("Para identificar o cliente é obrigatório informar o CPF");
         }

        if (this.dataHora == null){
            throw new DomainEntityException("Data/hora do pedido é obrigatória");
        }

        if (this.status == null){
            throw new DomainEntityException("Status do pedido não informado ou inválido");
        }

        if (this.statusPgto == null){
            throw new DomainEntityException("Status do pagamento não informado ou inválido");
        }

        if (this.produtos.isEmpty()){
            throw new DomainEntityException("O pedido deve possuir ao menos um produto");
        }

    }

    /**
     * 
     * @param id
     * @param dataHora
     * @param valor
     * @param status
     * @param statusPgto
     * @param produtos
     */
    public Pedido(String id, LocalDateTime dataHora, BigDecimal valor, String status, String statusPgto,
                    List<ComboPedido> produtos) {
        this(id, null, dataHora, status, statusPgto, produtos);
    }

    public StatusPedidoType getProximoStatus(){
        if (List.of(StatusPedidoType.CANCELADO, StatusPedidoType.FINALIZADO).contains(status)){
            return null;
        }

        return Stream.of(StatusPedidoType.values())
                    .filter(s -> StatusPedidoType.CANCELADO != s)
                    .sorted(Comparator.comparingInt(StatusPedidoType::getFlowOrder))
                    .filter(s -> s.getFlowOrder() == status.getFlowOrder()+1)
                    .findFirst()
                    .orElse(null);
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
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

    public List<ComboPedido> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
    
}
