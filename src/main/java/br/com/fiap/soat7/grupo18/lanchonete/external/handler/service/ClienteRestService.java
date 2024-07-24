package br.com.fiap.soat7.grupo18.lanchonete.external.handler.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.adapter.controller.ClienteController;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerResponseDto;
import jakarta.transaction.Transactional;

@Service
public class ClienteRestService {

    private final ClienteDataRepository clienteRepository;

    public ClienteRestService(@Qualifier("clienteDatabaseRepository") ClienteDataRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteHandlerResponseDto findByCpfCliente(String cpf){
        return new ClienteController(clienteRepository).findByCpfCliente(cpf);
    }

    @Transactional
    public ClienteHandlerResponseDto save(ClienteHandlerRequestDto clienteDto){
        return new ClienteController(clienteRepository).save(clienteDto);
    }

    

}
