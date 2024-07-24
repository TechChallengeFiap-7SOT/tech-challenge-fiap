package br.com.fiap.soat7.grupo18.lanchonete.core.repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository.DataRepository;

public interface ClienteDataRepository extends DataRepository {

    public Cliente findByCpfCliente(String cpf);
    public Cliente save(Cliente cliente);

}
