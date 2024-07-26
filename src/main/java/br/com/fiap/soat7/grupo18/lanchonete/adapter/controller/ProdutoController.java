package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.ProdutoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter.ProdutoPresenter;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.CategoriaUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ProdutoUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerResponseDto;

public class ProdutoController {
    
    private final ProdutoDataRepository produtoRepository;
    private final ProdutoUseCase produtoUseCase;

    public ProdutoController(ProdutoDataRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        produtoUseCase = new ProdutoUseCase(new ProdutoGateway(this.produtoRepository));
    }

    public ProdutoHandlerResponseDto findByIdProduto(String id){
        return ProdutoPresenter.maptoDto(produtoUseCase.findByIdProduto(id));
    }

    public ProdutoHandlerResponseDto save(ProdutoHandlerRequestDto produtoDto, CategoriaUseCase categoriaUseCase){
        return ProdutoPresenter.maptoDto(produtoUseCase.save(produtoDto, categoriaUseCase));
    }

    public List<ProdutoHandlerResponseDto> findByCategoria(Long idCategoria){
        return produtoUseCase.findByCategoria(idCategoria)
                    .stream()
                    .map(ProdutoPresenter::maptoDto)
                    .toList();
    }

    public List<ProdutoHandlerResponseDto> findAllByAtivoTrue(){
        return produtoUseCase.findAllByAtivoTrue()
                    .stream()
                    .map(ProdutoPresenter::maptoDto)
                    .toList();
    }

    public void delete(String id){
        produtoUseCase.delete(id);
    }

    public ProdutoHandlerResponseDto update(String idProduto, ProdutoHandlerRequestDto produtoDto, CategoriaUseCase categoriaUseCase){
        return ProdutoPresenter.maptoDto(produtoUseCase.update(idProduto, produtoDto, categoriaUseCase));
    }
    
    
}
