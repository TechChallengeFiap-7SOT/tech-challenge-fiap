package br.com.fiap.soat7.grupo18.lanchonete.core.entity.dto;

public class CategoriaDto {
    private final Long id;
    private final String nome;
    
    public CategoriaDto(Long id, String nome) {
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
