package br.com.fiap.soat7.grupo18.lanchonete.adapter.gateway;

import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Pedido;
import br.com.fiap.soat7.grupo18.lanchonete.core.repository.PedidoDataRepository;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;

public class PedidoGateway {

    private final PedidoDataRepository pedidoRepository;

    public PedidoGateway(PedidoDataRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido findById(String idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public int updateStatus(String idPedido, StatusPedidoType novoStatus) {
        return pedidoRepository.updateStatus(idPedido, novoStatus);
    }

    public int updateStatusPgto(String idPedido, StatusPgtoType novoStatusPgto) {
        return pedidoRepository.updateStatusPgto(idPedido, novoStatusPgto);
    }

    public StatusPgtoType getStatusPgto(String idPedido) {
        return pedidoRepository.getStatusPgto(idPedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> findNaoFinalizadosOuCancelados() {
        return pedidoRepository.findNaoFinalizadosOuCancelados();
    }

}
