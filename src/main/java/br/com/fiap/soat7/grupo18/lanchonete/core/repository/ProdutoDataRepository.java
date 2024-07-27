package br.com.fiap.soat7.grupo18.lanchonete.core.repository;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository.DataRepository;

public interface ProdutoDataRepository extends DataRepository {

    public Produto save(Produto produto);

    public Produto findByIdProduto(String id);
    
    public Produto findAtivoByIdProduto(String id);

    public List<Produto> findByCategoria(Long idCategoria);

    public List<Produto> findAllByAtivoTrue();

    public void delete(String id);

    public Produto update(Produto produto);




}
