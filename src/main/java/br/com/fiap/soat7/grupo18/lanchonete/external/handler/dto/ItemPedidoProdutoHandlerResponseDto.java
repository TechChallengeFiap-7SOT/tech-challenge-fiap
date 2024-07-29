package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.math.BigDecimal;

public class ItemPedidoProdutoHandlerResponseDto {

    private String idProduto;

    private String nomeProduto;

    private BigDecimal preco;

    /**
     * 
     * @param idProduto
     * @param nomeProduto
     * @param preco
     */
    public ItemPedidoProdutoHandlerResponseDto(String idProduto, String nomeProduto, BigDecimal preco) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public BigDecimal getPreco() {
        return preco;
    }


}
