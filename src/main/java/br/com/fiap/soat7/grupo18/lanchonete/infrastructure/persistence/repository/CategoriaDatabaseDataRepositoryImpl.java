package br.com.fiap.soat7.grupo18.lanchonete.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.dto.CategoriaDto;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.CategoriaDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.infrastructure.persistence.entity.CategoriaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository("categoriaDatabaseRepository")
public class CategoriaDatabaseDataRepositoryImpl extends DatabaseDataRepository implements CategoriaDataRepository {

    @Override
    public List<CategoriaDto> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CategoriaDto> query = cb.createQuery(CategoriaDto.class);
        Root<CategoriaEntity> root = query.from(CategoriaEntity.class);
        query.select(cb.construct(CategoriaDto.class, root.get("id"), root.get("nome")));
        query.orderBy(cb.asc(root.get("id")));

        return getEntityManager().createQuery(query).getResultList();
    }

}
