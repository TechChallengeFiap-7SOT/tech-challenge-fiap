package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ClienteDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.ClienteEntity;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository("clienteDatabaseRepository")
public class ClienteDatabaseDataRepositoryImpl extends DatabaseDataRepository implements ClienteDataRepository {

    @Override
    public Cliente findByCpfCliente(String cpf) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
        Root<ClienteEntity> root = query.from(ClienteEntity.class);
        Predicate[] predicates = new Predicate[1];
        predicates[0] = cb.like(root.get("cpf"), cpf);
        query.select(cb.construct(Cliente.class, root.get("cpf"), root.get("nome"), root.get("email")));
        query.where(predicates);
        try{
            return getEntityManager().createQuery(query).getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity(cliente.getCpf(), cliente.getNome(), cliente.getEmail());
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return new Cliente(entity.getCpf(), entity.getNome(), entity.getEmail());
    }

}
