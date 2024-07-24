package br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter;

import java.util.Optional;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerResponseDto;

public class ClientePresenter {

    /**
     * 
     * @param cliente
     * @return
     */
    public static ClienteHandlerResponseDto maptoDto(Cliente cliente){
        return Optional.ofNullable(cliente)
                    .map( c-> new ClienteHandlerResponseDto(c.getCpf(), c.getNome(), c.getEmail()))
                    .orElse(null);
    }

}
