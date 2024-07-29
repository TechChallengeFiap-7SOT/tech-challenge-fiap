package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComboPedidoHandlerRequestDto {

    private ItemPedidoProdutoHandlerRequestDto itemLanche;

    private ItemPedidoProdutoHandlerRequestDto itemAcompanhamento;

    private ItemPedidoProdutoHandlerRequestDto itemBebida;

    private ItemPedidoProdutoHandlerRequestDto itemSobremesa;

    public ComboPedidoHandlerRequestDto() {
    }

    /**
     * 
     * @param itemLanche
     * @param itemAcompanhamento
     * @param itemBebida
     * @param itemSobremesa
     */
    public ComboPedidoHandlerRequestDto(ItemPedidoProdutoHandlerRequestDto itemLanche,
            ItemPedidoProdutoHandlerRequestDto itemAcompanhamento, ItemPedidoProdutoHandlerRequestDto itemBebida,
            ItemPedidoProdutoHandlerRequestDto itemSobremesa) {
        this.itemLanche = itemLanche;
        this.itemAcompanhamento = itemAcompanhamento;
        this.itemBebida = itemBebida;
        this.itemSobremesa = itemSobremesa;
    }

    public ItemPedidoProdutoHandlerRequestDto getItemLanche() {
        return itemLanche;
    }

    public ItemPedidoProdutoHandlerRequestDto getItemAcompanhamento() {
        return itemAcompanhamento;
    }

    public ItemPedidoProdutoHandlerRequestDto getItemBebida() {
        return itemBebida;
    }

    public ItemPedidoProdutoHandlerRequestDto getItemSobremesa() {
        return itemSobremesa;
    }

    public void setItemLanche(ItemPedidoProdutoHandlerRequestDto itemLanche) {
        this.itemLanche = itemLanche;
    }

    public void setItemAcompanhamento(ItemPedidoProdutoHandlerRequestDto itemAcompanhamento) {
        this.itemAcompanhamento = itemAcompanhamento;
    }

    public void setItemBebida(ItemPedidoProdutoHandlerRequestDto itemBebida) {
        this.itemBebida = itemBebida;
    }

    public void setItemSobremesa(ItemPedidoProdutoHandlerRequestDto itemSobremesa) {
        this.itemSobremesa = itemSobremesa;
    }


}
