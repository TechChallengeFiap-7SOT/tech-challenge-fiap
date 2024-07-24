package br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter;

import java.util.Optional;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerResponseDto;

public class CategoriaPresenter {

    /**
     * @param categoria
     * @return
     */
    public static CategoriaHandlerResponseDto mapToDto(Categoria categoria){
        return Optional.ofNullable(categoria)
                    .map(c -> new CategoriaHandlerResponseDto(c.getId(), c.getNome()))
                    .orElse(null);
    }

}
