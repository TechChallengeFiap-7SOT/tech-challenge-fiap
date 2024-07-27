package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Categoria {

    public static final long CATEGORIA_LANCHE = 1L;
    public static final long CATEGORIA_ACOMPANHAMENTO = 2L;
    public static final long CATEGORIA_BEBIDA = 3L;
    public static final long CATEGORIA_SOBREMESA = 4L;

    private final Long id;
    private final String nome;
    
    

    public Categoria(Long id) {
        boolean validId = id != null && id > 0L;
        if (!validId){
            throw new DomainEntityException("ID é obrigatório para referenciar uma categoria");
        }
        this.id = id;
        this.nome = null;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Categoria other = (Categoria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
