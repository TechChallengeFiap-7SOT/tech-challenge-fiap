package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;

@Repository("produtoDatabaseRepository")
public class ProdutoDatabaseRepositoryImpl extends DatabaseDataRepository implements ProdutoDataRepository {

    @Override
    public Produto save(Produto produto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Produto findByIdProduto(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByIdProduto'");
    }

    @Override
    public List<Produto> findByCategoria(Long idCategoria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCategoria'");
    }

    @Override
    public List<Produto> findAllByAtivoTrue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByAtivoTrue'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Produto update(Produto produto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
