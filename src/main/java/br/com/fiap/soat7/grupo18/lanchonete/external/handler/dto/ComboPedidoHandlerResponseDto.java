package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class ComboPedidoHandlerResponseDto {

    private Map<CategoriaHandlerResponseDto, ItemPedidoProdutoHandlerResponseDto> conteudoCombo;

    private BigDecimal preco;

    private Integer comboNum;

    /**
     * 
     * @param conteudoCombo
     * @param preco
     * @param comboNum
     */
    public ComboPedidoHandlerResponseDto(Map<CategoriaHandlerResponseDto, ItemPedidoProdutoHandlerResponseDto> conteudoCombo,
                                            BigDecimal preco, Integer comboNum) {
        this.conteudoCombo = conteudoCombo != null ? Collections.unmodifiableMap(conteudoCombo) : Map.of();
        this.preco = preco;
        this.comboNum = comboNum;
    }

    public Map<CategoriaHandlerResponseDto, ItemPedidoProdutoHandlerResponseDto> getConteudoCombo() {
        return Map.copyOf(conteudoCombo);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getComboNum() {
        return comboNum;
    }
    
}
