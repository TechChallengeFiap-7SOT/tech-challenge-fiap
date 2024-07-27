package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.math.BigDecimal;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class ItemPedidoProduto {

    private final String idProduto;

    private final String nomeProduto;

    private final BigDecimal preco;

    
    /**
     * 
     * @param idProduto
     * @param nomeProduto
     * @param preco
     */
    public ItemPedidoProduto(String idProduto, String nomeProduto, BigDecimal preco) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;

        if (idProduto == null || idProduto.isBlank()){
            throw new DomainEntityException("ID do produto é obrigatório");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new DomainEntityException("O preço do produto é obrigatório, onde o mesmo deve ser maior que 0.00");
        }
    }

    /**
     * 
     * @param idProduto
     * @param preco
     */
    public ItemPedidoProduto(String idProduto, BigDecimal preco) {
        this(idProduto, null, preco);
    }

    public String getIdProduto() {
        return idProduto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }


    

}
