package br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;

public class ProdutoGateway {

    private final ProdutoDataRepository produtoRepository;

    public ProdutoGateway(ProdutoDataRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto save(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto findByIdProduto(String id){
        return produtoRepository.findByIdProduto(id);
    }

    public List<Produto> findByCategoria(Long idCategoria){
        return produtoRepository.findByCategoria(idCategoria);
    }

    public List<Produto> findAllByAtivoTrue(){
        return produtoRepository.findAllByAtivoTrue();
    }

    public void delete(String id){
        produtoRepository.delete(id);
    }

    public Produto update(Produto produto){
        return produtoRepository.update(produto);
    }

}
