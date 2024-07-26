package br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter;

import java.util.Optional;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerResponseDto;

public class ProdutoPresenter {

    public static ProdutoHandlerResponseDto maptoDto(Produto produto){
        return Optional.ofNullable(produto)
                    .map(p -> new ProdutoHandlerResponseDto(p.getId(), p.getNome(), p.getDescricao(),
                                                                p.getPreco(),
                                                                new CategoriaHandlerResponseDto(p.getCategoria().getId(), p.getCategoria().getNome()),
                                                                p.isAtivo()))
                    .orElse(null);
    }

}
