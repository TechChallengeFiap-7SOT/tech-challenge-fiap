package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Produto;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.ProdutoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.CategoriaEntity;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.ProdutoEntity;
import jakarta.persistence.NoResultException;

@Repository("produtoDatabaseRepository")
public class ProdutoDatabaseRepositoryImpl extends DatabaseDataRepository implements ProdutoDataRepository {

    @Override
    public Produto save(Produto produto) {
        ProdutoEntity produtoEntity = getEntityFromProduto(produto);
        getEntityManager().persist(produtoEntity);
        getEntityManager().flush();
        var savedEntity = getEntityManager().find(ProdutoEntity.class, produtoEntity.getId());
        return getProdutoFromEntity(savedEntity);
    }

    @Override
    public Produto findByIdProduto(String id) {
        final String jpql = "select p from ProdutoEntity p join fetch p.categoria where p.id = :id";
        try{
            var produtoEntity = getEntityManager().createQuery(jpql, ProdutoEntity.class)
                                    .setParameter("id", id)
                                    .getSingleResult();
            return getProdutoFromEntity(produtoEntity);
        }catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public Produto findAtivoByIdProduto(String id) {
        final String jpql = "select p from ProdutoEntity p join fetch p.categoria where p.id = :id and p.ativo = true";
        try{
            var produtoEntity = getEntityManager().createQuery(jpql, ProdutoEntity.class)
                                    .setParameter("id", id)
                                    .getSingleResult();
            return getProdutoFromEntity(produtoEntity);
        }catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public List<Produto> findByCategoria(Long idCategoria) {
        final String jpql = "select p from ProdutoEntity p join fetch p.categoria c where c.id = :idCategoria";
        return getEntityManager().createQuery(jpql, ProdutoEntity.class)
                        .setParameter("idCategoria", idCategoria)
                        .getResultList()
                        .stream()
                        .map(this::getProdutoFromEntity)
                        .toList();
    }

    @Override
    public List<Produto> findAllByAtivoTrue() {
        final String jpql = "select p from ProdutoEntity p where p.ativo = true";
        return getEntityManager().createQuery(jpql, ProdutoEntity.class)
                        .getResultList()
                        .stream()
                        .map(this::getProdutoFromEntity)
                        .toList();
    }

    @Override
    public void delete(String id) {
        final String ql = "update ProdutoEntity p set p.ativo = false where p.id = :id";
        getEntityManager().createQuery(ql)
                    .setParameter("id", id)
                    .executeUpdate();
        getEntityManager().flush();
    }

    @Override
    public Produto update(Produto produto) {
        var produtoEntity = getEntityFromProduto(produto);
        produtoEntity = getEntityManager().merge(produtoEntity);
        getEntityManager().flush();
        return getProdutoFromEntity(produtoEntity);
    }

    private ProdutoEntity getEntityFromProduto(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(produto.getCategoria().getId());
        categoriaEntity.setNome(produto.getCategoria().getNome());
        produtoEntity.setId(produto.getId());
        produtoEntity.setAtivo(produto.isAtivo());
        produtoEntity.setCategoria(categoriaEntity);
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setPreco(produto.getPreco());
        return produtoEntity;
    }

    private Produto getProdutoFromEntity(ProdutoEntity produtoEntity){
        Categoria categoria = new Categoria(produtoEntity.getCategoria().getId(), produtoEntity.getCategoria().getNome());
        return new Produto(produtoEntity.getId(), produtoEntity.getNome(), produtoEntity.getDescricao(),
                                    produtoEntity.getPreco(), categoria, produtoEntity.isAtivo());
    }

}
