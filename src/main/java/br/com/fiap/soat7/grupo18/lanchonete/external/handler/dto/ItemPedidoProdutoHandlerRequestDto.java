package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

public class ItemPedidoProdutoHandlerRequestDto {

    private String idProduto;

    public ItemPedidoProdutoHandlerRequestDto() {
    }

    public ItemPedidoProdutoHandlerRequestDto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }


}
