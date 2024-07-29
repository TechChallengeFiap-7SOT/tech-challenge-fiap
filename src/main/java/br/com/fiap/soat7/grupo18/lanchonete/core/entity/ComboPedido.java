package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class ComboPedido {

    private final String idPedido;

    private final Map<Categoria, ItemPedidoProduto> mapaCombo;

    private final BigDecimal preco;

    private final Integer comboNum;

    
    /**
     * 
     * @param idPedido
     * @param mapaCombo
     * @param comboNum
     */
    public ComboPedido(String idPedido, Map<Categoria, ItemPedidoProduto> mapaCombo, Integer comboNum) {
        this.idPedido = idPedido;
        this.mapaCombo = mapaCombo != null ? Collections.unmodifiableMap(mapaCombo) : Map.of();
        this.comboNum = comboNum;
        this.preco = mapaCombo.values().stream()
                            .filter(i -> i != null)
                            .map(ItemPedidoProduto::getPreco)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (this.mapaCombo.isEmpty() || this.mapaCombo.values().stream().allMatch(Objects::isNull)){
            throw new DomainEntityException("É obrigatório informar ao menos um item para o combo do pedido");
        }

        if (comboNum == null || comboNum < 1){
            throw new DomainEntityException("Número do combo é obrigatório");
        }
    }

    
    /**
     * 
     * @param mapaCombo
     * @param comboNum
     */
    public ComboPedido(Map<Categoria, ItemPedidoProduto> mapaCombo, Integer comboNum) {
        this(null, mapaCombo, comboNum);
    }



    public String getIdPedido() {
        return idPedido;
    }

    public Map<Categoria, ItemPedidoProduto> getMapaCombo() {
        return Collections.unmodifiableMap(mapaCombo);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getComboNum() {
        return comboNum;
    }
    
}
