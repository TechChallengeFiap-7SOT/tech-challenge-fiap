package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.math.BigDecimal;

public class ProdutoHandlerRequestDto {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private CategoriaHandlerRequestDto categoria;
    
    /**
     * 
     * @param nome
     * @param descricao
     * @param preco
     * @param categoria
     */
    public ProdutoHandlerRequestDto(String nome, String descricao, BigDecimal preco, CategoriaHandlerRequestDto categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public static ProdutoHandlerRequestDto newEmptyInstance(){
        return new ProdutoHandlerRequestDto(null, null, null, new CategoriaHandlerRequestDto(null));
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public CategoriaHandlerRequestDto getCategoria() {
        return categoria;
    }
    
}
