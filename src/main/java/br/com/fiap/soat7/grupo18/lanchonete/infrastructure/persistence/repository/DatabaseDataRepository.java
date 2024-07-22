package br.com.fiap.soat7.grupo18.lanchonete.infrastructure.persistence.repository;

import br.com.fiap.soat7.grupo18.lanchonete.infrastructure.config.DatabaseConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class DatabaseDataRepository implements DataRepository {

    @PersistenceContext(unitName = DatabaseConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
