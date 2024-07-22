package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;

@Service
public class CategoriaRestService {

    private final CategoriaDataRepository categoriaRepository;

    public CategoriaRestService(@Qualifier("categoriaDatabaseRepository") CategoriaDataRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    

}
