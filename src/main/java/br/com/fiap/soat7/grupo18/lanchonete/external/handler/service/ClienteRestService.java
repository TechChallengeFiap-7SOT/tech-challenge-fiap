package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.ClienteController;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerResponseDto;

@Service
public class ClienteRestService {

    private final ClienteController clienteController;

    public ClienteRestService(@Qualifier("clienteDatabaseRepository") ClienteDataRepository clienteRepository) {
        this.clienteController = new ClienteController(clienteRepository);
    }

    public ClienteHandlerResponseDto findByCpfCliente(String cpf){
        return clienteController.findByCpfCliente(cpf);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ClienteHandlerResponseDto save(ClienteHandlerRequestDto clienteDto){
        return clienteController.save(clienteDto);
    }

    

}
