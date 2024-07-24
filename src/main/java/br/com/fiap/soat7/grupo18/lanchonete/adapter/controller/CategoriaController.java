package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.CategoriaGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter.CategoriaPresenter;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.CategoriaUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerResponseDto;

public class CategoriaController {

    private final CategoriaDataRepository categoriaRepository;
    private final CategoriaUseCase useCase;

    public CategoriaController(CategoriaDataRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        useCase = new CategoriaUseCase(new CategoriaGateway(this.categoriaRepository));
    }

    public List<CategoriaHandlerResponseDto> findAll() {
        return useCase.findAll()
                    .stream()
                    .map(CategoriaPresenter::mapToDto)
                    .toList();
    }

    

}
