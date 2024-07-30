package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Categoria;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Cliente;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.ComboPedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.ItemPedidoProduto;
import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Pedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.PedidoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.ClienteEntity;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.PedidoEntity;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.PedidoProdutoEntity;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity.ProdutoEntity;
import jakarta.persistence.NoResultException;

@Repository("pedidoDatabaseRepository")
public class PedidoDatabaseRepositoryImpl extends DatabaseDataRepository implements PedidoDataRepository {

    
    
    @Override
    public Pedido findById(String idPedido) {
        PedidoEntity entity = findSaved(idPedido);
        return entity != null ? getPedidoFromEntity(entity) : null;
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoEntity entity = getEntityFromPedido(pedido);
        getEntityManager().persist(entity);
        getEntityManager().flush();
        getEntityManager().clear();
        return getPedidoFromEntity(findSaved(pedido.getId()));
    }

    @Override
    public int updateStatus(String idPedido, StatusPedidoType novoStatus) {
        final String ql = "update PedidoEntity p set p.status = :status where p.id = :idPedido";
        int updated = getEntityManager().createQuery(ql)
                        .setParameter("status", novoStatus)
                        .setParameter("idPedido", idPedido)
                        .executeUpdate();
        getEntityManager().clear();
        return updated;
    }

    @Override
    public int updateStatusPgto(String idPedido, StatusPgtoType novoStatusPgto, String idTransacaoPgto) {
        final String ql = "update PedidoEntity p set p.statusPgto = :status, p.idTransacaoPagamento = :idTransacao where p.id = :idPedido";
        int updated = getEntityManager().createQuery(ql)
                        .setParameter("status", novoStatusPgto)
                        .setParameter("idTransacao", idTransacaoPgto)
                        .setParameter("idPedido", idPedido)
                        .executeUpdate();
        getEntityManager().clear();
        return updated;
    }

    @Override
    public StatusPgtoType getStatusPgto(String idPedido) {
        final String sql = "select p.statusPgto from PedidoEntity p where p.id = :idPedido";
        try{
            return (StatusPgtoType) getEntityManager().createQuery(sql)
                        .setParameter("idPedido", idPedido)
                        .getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public List<Pedido> findAll() {
        final String jpql = "select p from PedidoEntity p join fetch p.produtos prods";
        return getEntityManager().createQuery(jpql, PedidoEntity.class)
                    .getResultList()
                    .stream()
                    .map(this::getPedidoFromEntity)
                    .collect(Collectors.toUnmodifiableList());
        
    }

    @Override
    public List<Pedido> findNaoFinalizadosOuCancelados() {
        final String jpql = "select p from PedidoEntity p join fetch p.produtos prods where p.status not in (:excludeStatus)";
        return getEntityManager().createQuery(jpql, PedidoEntity.class)
                    .setParameter("excludeStatus", List.of(StatusPedidoType.FINALIZADO, StatusPedidoType.CANCELADO))
                    .getResultList()
                    .stream()
                    .map(this::getPedidoFromEntity)
                    .collect(Collectors.toUnmodifiableList());
    }

    private PedidoEntity findSaved(String id){
        final String jpql = "select p from PedidoEntity p left join fetch p.cliente join fetch p.produtos prods where p.id = :id";
        try{
            return getEntityManager().createQuery(jpql, PedidoEntity.class)
                            .setParameter("id", id)
                            .getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }

    private Pedido getPedidoFromEntity(PedidoEntity savedEntity) {
        Map<Integer, List<PedidoProdutoEntity>> mapaCombos = savedEntity.getProdutos().stream().collect(Collectors.groupingBy(PedidoProdutoEntity::getComboNum));
        List<ComboPedido> produtos = new ArrayList<>();
        Cliente cliente = savedEntity.getCliente() != null ? new Cliente(savedEntity.getCliente().getCpf(),
                                                                        savedEntity.getCliente().getNome(),
                                                                        savedEntity.getCliente().getEmail())
                                                            : null;
        for (var entrySet : mapaCombos.entrySet()){
            final Integer comboNum = entrySet.getKey();
            Map<Categoria, ItemPedidoProduto> mapaItensCombo = entrySet.getValue().stream()
                                                                    .collect(Collectors.toUnmodifiableMap(
                                                                        key -> {
                                                                            var categoriaEntity = key.getProduto().getCategoria();
                                                                            return new Categoria(categoriaEntity.getId(),
                                                                                    categoriaEntity.getNome());
                                                                        }    
                                                                    , value -> {
                                                                        var produtoEntity = value.getProduto();
                                                                        return new ItemPedidoProduto(
                                                                            produtoEntity.getId(),
                                                                            produtoEntity.getNome(),
                                                                            produtoEntity.getPreco()
                                                                        );
                                                                    }));

            ComboPedido combo = new ComboPedido(savedEntity.getId(), mapaItensCombo, comboNum);
            produtos.add(combo);
        }

        return new Pedido(savedEntity.getId(), cliente, savedEntity.getDataHora(), savedEntity.getStatus().name(),
                                savedEntity.getStatusPgto().name(), produtos);
    }

    private PedidoEntity getEntityFromPedido(Pedido pedido) {
        ClienteEntity cliente = pedido.getCliente() != null ? ClienteEntity.builder()
                                                                    .cpf(pedido.getCliente().getCpf())
                                                                    .build()
                                                            : null;
        
        var produtos = pedido.getProdutos().stream()
                        .map(p -> {
                            return p.getMapaCombo().values()
                                        .stream()
                                        .map(itemCombo ->{
                                            var produto = ProdutoEntity.builder()
                                                                .id(itemCombo.getIdProduto())
                                                                .build();
                                            return PedidoProdutoEntity.builder()
                                                        .comboNum(p.getComboNum())
                                                        .preco(p.getPreco())
                                                        .produto(produto)
                                                        .pedido(PedidoEntity.builder().id(pedido.getId()).build())
                                                        .build();
                                        })
                                        .toList();
                        })
                        .toList()
                        .stream()
                        .flatMap(List::stream) //isso é feito para converter a lista de listas em uma única lista
                        .toList();

        return PedidoEntity.builder()
                        .id(pedido.getId())
                        .dataHora(pedido.getDataHora())
                        .valor(pedido.getValor())
                        .status(pedido.getStatus())
                        .statusPgto(pedido.getStatusPgto())
                        .cliente(cliente)
                        .produtos(produtos)
                        .build();
    }

}
