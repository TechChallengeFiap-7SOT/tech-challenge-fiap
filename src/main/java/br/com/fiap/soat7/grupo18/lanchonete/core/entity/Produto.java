package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Produto {

    private final String id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;
    private final Categoria categoria;
    private final boolean ativo;
    
    /**
     * 
     * @param id
     * @param nome
     * @param descricao
     * @param preco
     * @param categoria
     * @param ativo
     */
    public Produto(String id, String nome, String descricao, BigDecimal preco, Categoria categoria, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.ativo = ativo;

        validateProduto();
    }

    /**
     * 
     * @param nome
     * @param descricao
     * @param preco
     * @param categoria
     */
    public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.id = null;
        this.ativo = true;

        validateProduto();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isCategoriaLanche(){
        return Categoria.CATEGORIA_LANCHE == categoria.getId();
    }

    public boolean isCategoriaAcompanhamento(){
        return Categoria.CATEGORIA_ACOMPANHAMENTO == categoria.getId();
    }

    public boolean isCategoriaBebida(){
        return Categoria.CATEGORIA_BEBIDA == categoria.getId();
    }

    public boolean isCategoriaSobremesa(){
        return Categoria.CATEGORIA_SOBREMESA == categoria.getId();
    }

    private void validateProduto() {
        if (Optional.ofNullable(nome).orElse("").isBlank()){
            throw new DomainEntityException("Nome do produto é obrigatório");
        }

        if (Optional.ofNullable(descricao).orElse("").isBlank()){
            throw new DomainEntityException("Descrição do produto é obrigatória");
        }

        if (Optional.ofNullable(preco).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) < 1){
            throw new DomainEntityException("Preço deve ser maior que 0.00");
        }

        Optional.ofNullable(categoria).map(Categoria::getId).orElseThrow(() -> new DomainEntityException("Categoria do produto é obrigatória"));

        if (Stream.of(isCategoriaLanche(), isCategoriaAcompanhamento(), isCategoriaBebida(), isCategoriaSobremesa()).allMatch(i -> !i)){
            throw new DomainEntityException("Categoria de produto inválida");
        }

        if (id == null && !ativo){
            throw new DomainEntityException("Não é possível cadastrar um produto com status 'inativo'");
        }
    }

    

    



    
}
