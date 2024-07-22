package br.com.fiap.soat7.grupo18.lanchonete.core.repository;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository.DataRepository;

public interface CategoriaDataRepository extends DataRepository {

    public List<Categoria> findAll();

}
