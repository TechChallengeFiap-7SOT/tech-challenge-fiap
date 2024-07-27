package br.com.fiap.soat7.grupo18.lanchonete.core.repository;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Pedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.repository.DataRepository;

public interface PedidoDataRepository extends DataRepository {

    public Pedido save(Pedido pedido);

    public Pedido findById(String idPedido);

    public int updateStatus(String idPedido, StatusPedidoType novoStatus);

    public int updateStatusPgto(String idPedido, StatusPgtoType novoStatusPgto);

    public StatusPgtoType getStatusPgto(String idPedido);

    public List<Pedido> findAll();

    public List<Pedido> findNaoFinalizadosOuCancelados();
}
