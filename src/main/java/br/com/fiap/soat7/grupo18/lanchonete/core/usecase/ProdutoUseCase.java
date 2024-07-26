package br.com.fiap.soat7.grupo18.lanchonete.core.usecase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.ProdutoGateway;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.NotFoundEntityException;

public class ProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public ProdutoUseCase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto findByIdProduto(String id){
        return id == null || id.isBlank() ? null : produtoGateway.findByIdProduto(id);
    }

    public Produto save(ProdutoHandlerRequestDto produtoDto, CategoriaUseCase categoriaUseCase){
        final boolean STATUS_ATIVO = true;
        if (produtoDto == null){
            produtoDto = ProdutoHandlerRequestDto.newEmptyInstance();
        }

        var categoria = findCategoria(produtoDto, categoriaUseCase);

        Produto produto = new Produto(UUID.randomUUID().toString(),
                                        produtoDto.getNome(),
                                        produtoDto.getDescricao(),
                                        produtoDto.getPreco(),
                                        categoria,
                                        STATUS_ATIVO);
        return produtoGateway.save(produto);
    }

    public List<Produto> findByCategoria(Long idCategoria){
        return idCategoria == null ? Collections.emptyList() : getListSortedByNomeProduto(produtoGateway.findByCategoria(idCategoria));
    }

    public List<Produto> findAllByAtivoTrue(){
        return getListSortedByNomeProduto(produtoGateway.findAllByAtivoTrue());
    }

    public void delete(String id){
        if (findByIdProduto(id) == null){
            throw new NotFoundEntityException("Produto não localizado com o ID informado");
        }
        produtoGateway.delete(id);
    }

    public Produto update(String idProduto, ProdutoHandlerRequestDto produtoDto, CategoriaUseCase categoriaUseCase){
        final boolean STATUS_ATIVO = true; //o status é sempre ativo. Para desativar, efetua-se o soft delete...
        Produto produto = findByIdProduto(idProduto);
        if (produto == null || !produto.isAtivo()){
            throw new NotFoundEntityException("Produto não localizado com o ID informado");
        }

        var categoria = findCategoria(produtoDto == null ? ProdutoHandlerRequestDto.newEmptyInstance() : produtoDto, categoriaUseCase);

        produto = new Produto(idProduto,
                            produtoDto.getNome(),
                            produtoDto.getDescricao(),
                            produtoDto.getPreco(),
                            categoria,
                            STATUS_ATIVO);

        return produtoGateway.update(produto);
    }

    private Categoria findCategoria(ProdutoHandlerRequestDto produtoDto, CategoriaUseCase categoriaUseCase) {
        var categoria = Optional.ofNullable(
                                categoriaUseCase.findById(
                                                    Optional.ofNullable(produtoDto.getCategoria())
                                                                    .map(CategoriaHandlerRequestDto::getId)
                                                                    .orElse(null)
                                                    )
                            ).orElseThrow(() -> new NotFoundEntityException("Categoria não localizada com o ID informado"));
        return categoria;
    }

    private List<Produto> getListSortedByNomeProduto(List<Produto> lista){
        return lista.stream()
                .sorted(Comparator.comparing(Produto::getNome))
                .toList();
    }

    //prosseguir implementando controller, services e restHandler de produto...
    
}
