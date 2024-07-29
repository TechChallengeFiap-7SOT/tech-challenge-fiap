package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.ProdutoController;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.CategoriaGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.CategoriaUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerResponseDto;

@Service
public class ProdutoRestService {

    private final CategoriaUseCase categoriaUseCase;
    private final ProdutoController produtoController;

    public ProdutoRestService(@Qualifier("produtoDatabaseRepository") ProdutoDataRepository produtoRepository,
                                @Qualifier("categoriaDatabaseRepository") CategoriaDataRepository categoriaRepository) {
        this.categoriaUseCase = new CategoriaUseCase(new CategoriaGateway(categoriaRepository));
        this.produtoController = new ProdutoController(produtoRepository);
    }

    public ProdutoHandlerResponseDto findByIdProduto(String id){
        return produtoController.findByIdProduto(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProdutoHandlerResponseDto save(ProdutoHandlerRequestDto produtoDto){
        return produtoController.save(produtoDto, categoriaUseCase);
    }

    public List<ProdutoHandlerResponseDto> findByCategoria(Long idCategoria){
        return produtoController.findByCategoria(idCategoria);
    }

    public List<ProdutoHandlerResponseDto> findAllByAtivoTrue(){
        return produtoController.findAllByAtivoTrue();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(String id){
        produtoController.delete(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProdutoHandlerResponseDto update(String idProduto, ProdutoHandlerRequestDto produtoDto){
        return produtoController.update(idProduto, produtoDto, categoriaUseCase);
    }

    
}
