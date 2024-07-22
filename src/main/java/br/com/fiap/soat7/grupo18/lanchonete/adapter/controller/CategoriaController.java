package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.CategoriaGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter.CategoriaPresenter;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.dto.CategoriaDto;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.CategoriaUseCase;

public class CategoriaController {

    private final CategoriaDataRepository categoriaRepository;

    public CategoriaController(CategoriaDataRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDto> findAll() {
        final CategoriaUseCase useCase = new CategoriaUseCase(new CategoriaGateway(categoriaRepository));
        return useCase.findAll()
                    .stream()
                    .map(CategoriaPresenter::mapToDto)
                    .toList();
    }

    

}
