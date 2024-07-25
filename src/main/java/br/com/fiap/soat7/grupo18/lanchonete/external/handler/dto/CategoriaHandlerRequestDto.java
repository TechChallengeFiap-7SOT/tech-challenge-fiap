package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

public class CategoriaHandlerRequestDto {

    private Long id;

    public CategoriaHandlerRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
