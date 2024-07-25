package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.math.BigDecimal;

public class ProdutoHandlerResponseDto {
    
    private String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private CategoriaHandlerResponseDto categoria;
    private boolean ativo;
    
    public ProdutoHandlerResponseDto(String id, String nome, String descricao, BigDecimal preco, CategoriaHandlerResponseDto categoria, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.ativo = ativo;
    }

    public String getId() {
        return id;
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

    public CategoriaHandlerResponseDto getCategoria() {
        return categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }
    
}
