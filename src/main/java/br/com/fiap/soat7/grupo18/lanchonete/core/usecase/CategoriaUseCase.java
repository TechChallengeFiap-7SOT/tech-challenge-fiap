package br.com.fiap.soat7.grupo18.lanchonete.core.usecase;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.CategoriaGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;

public class CategoriaUseCase {

    private final CategoriaGateway categoriaGateway;

    public CategoriaUseCase(CategoriaGateway gateway) {
        this.categoriaGateway = gateway;
    }

    public List<Categoria> findAll(){
        return categoriaGateway.findAll();
    }

    public Categoria findById(Long id){
        return id == null ? null : categoriaGateway.findById(id);
    }

    

}
