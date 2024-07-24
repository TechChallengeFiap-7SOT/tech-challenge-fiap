package br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;

public class ClienteGateway {

    private final ClienteDataRepository clienteRepository;

    public ClienteGateway(ClienteDataRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente findByCpfCliente(String cpf){
        return clienteRepository.findByCpfCliente(cpf);
    }

    

}
