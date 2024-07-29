package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.util.Optional;

public class CategoriaHandlerResponseDto {
    private Long id;
    private String nome;

    
    public CategoriaHandlerResponseDto() {
    }

    public CategoriaHandlerResponseDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        CategoriaHandlerResponseDto other = (CategoriaHandlerResponseDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Optional.ofNullable(nome).orElse("NULL").trim().toUpperCase();
    }

    

    
}
