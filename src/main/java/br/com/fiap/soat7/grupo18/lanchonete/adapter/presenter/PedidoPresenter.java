package br.com.fiap.soat7.grupo18.lanchonete.adapter.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.fiap.soat7.grupo18.lanchonete.core.entity.Pedido;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.CategoriaHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ComboPedidoHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ItemPedidoProdutoHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerResponseDto;

public class PedidoPresenter {


    public static PedidoHandlerResponseDto mapToDto(Pedido pedido){
        return Optional.ofNullable(pedido)
                        .map(p -> {
                            var clienteDto = ClientePresenter.maptoDto(pedido.getCliente());
                            List<ComboPedidoHandlerResponseDto> combos = new ArrayList<>(pedido.getProdutos().size());
                            for (var prod: pedido.getProdutos()){
                                Map<CategoriaHandlerResponseDto, ItemPedidoProdutoHandlerResponseDto> conteudoCombo = new HashMap<>(prod.getMapaCombo().size());
                                for (var entry : prod.getMapaCombo().entrySet()){
                                    if (entry.getValue() == null){
                                        continue;
                                    }
                                    var categoriaDto = CategoriaPresenter.mapToDto(entry.getKey());
                                    var itemPedidoDto = new ItemPedidoProdutoHandlerResponseDto(entry.getValue().getIdProduto(), entry.getValue().getNomeProduto(),
                                                                                                                            entry.getValue().getPreco());
                                    conteudoCombo.put(categoriaDto, itemPedidoDto);
                                }
                                var combo = new ComboPedidoHandlerResponseDto(conteudoCombo, prod.getPreco(), prod.getComboNum());
                                combos.add(combo);
                            }
                            return new PedidoHandlerResponseDto(p.getId(), p.getDataHora(), p.getValor(), p.getStatus(),
                                p.getStatusPgto(), clienteDto, combos);
                        })
                        .orElse(null);
    }
}
