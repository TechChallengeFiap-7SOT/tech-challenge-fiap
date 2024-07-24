package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.CategoriaController;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerResponseDto;

@Service
public class CategoriaRestService {

    private final CategoriaDataRepository categoriaRepository;

    public CategoriaRestService(@Qualifier("categoriaDatabaseRepository") CategoriaDataRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaHandlerResponseDto> findAll(){
        return new CategoriaController(categoriaRepository).findAll();
    }

    

}
