package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Categoria {

    private final Long id;
    private final String nome;
    
    /**
     * 
     * @param id
     * @param nome
     */
    public Categoria(Long id, String nome) {
        boolean validId = id != null && id > 0L;
        boolean validNome = nome != null && !nome.isEmpty();

        if (!(validId && validNome)){
            throw new DomainEntityException("ID e nome são obrigatórios para criar uma categoria");
        }

        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
