package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.ProdutoController;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.CategoriaGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.CategoriaUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerResponseDto;
import jakarta.transaction.Transactional;

@Service
public class ProdutoRestService {

    private final ProdutoDataRepository produtoRepository;
    private final CategoriaUseCase categoriaUseCase;

    public ProdutoRestService(@Qualifier("produtoDatabaseRepository") ProdutoDataRepository produtoRepository,
                                @Qualifier("categoriaDatabaseRepository") CategoriaDataRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaUseCase = new CategoriaUseCase(new CategoriaGateway(categoriaRepository));
    }

    public ProdutoHandlerResponseDto findByIdProduto(String id){
        return new ProdutoController(produtoRepository).findByIdProduto(id);
    }

    @Transactional
    public ProdutoHandlerResponseDto save(ProdutoHandlerRequestDto produtoDto){
        return new ProdutoController(produtoRepository).save(produtoDto, categoriaUseCase);
    }

    public List<ProdutoHandlerResponseDto> findByCategoria(Long idCategoria){
        return new ProdutoController(produtoRepository).findByCategoria(idCategoria);
    }

    public List<ProdutoHandlerResponseDto> findAllByAtivoTrue(){
        return new ProdutoController(produtoRepository).findAllByAtivoTrue();
    }

    @Transactional
    public void delete(String id){
        new ProdutoController(produtoRepository).delete(id);
    }

    @Transactional
    public ProdutoHandlerResponseDto update(String idProduto, ProdutoHandlerRequestDto produtoDto){
        return new ProdutoController(produtoRepository).update(idProduto, produtoDto, categoriaUseCase);
    }

    
}
