package br.com.fiap.soat7.grupo18.lanchonete.adapter.controller;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway.ClienteGateway;
import br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter.ClientePresenter;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.usecase.ClienteUseCase;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerResponseDto;

public class ClienteController {

    private final ClienteDataRepository clienteRepository;
    private final ClienteUseCase useCase;


    public ClienteController(ClienteDataRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        useCase = new ClienteUseCase(new ClienteGateway(this.clienteRepository));
    }

    public ClienteHandlerResponseDto findByCpfCliente(String cpf){
        return ClientePresenter.maptoDto(useCase.findByCpfCliente(cpf));
    }

    public ClienteHandlerResponseDto save(ClienteHandlerRequestDto clienteDto){
        return ClientePresenter.maptoDto(useCase.save(clienteDto));
    }

    

}
