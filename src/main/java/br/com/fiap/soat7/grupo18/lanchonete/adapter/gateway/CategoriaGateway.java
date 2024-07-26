package br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;

public class CategoriaGateway {

    private final CategoriaDataRepository categoriaRepository;

    public CategoriaGateway(CategoriaDataRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id){
        return categoriaRepository.findById(id);
    }

    
}
