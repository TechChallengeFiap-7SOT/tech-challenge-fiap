package br.com.fiap.soat7.grupo18.lanchonete.external.paymentgateway;

import java.math.BigDecimal;

public abstract class AbstractPagamentoGateway {

    public abstract String geraRequisicaoPgto(PagamentoOrder pgtoOrder);

    public static class PagamentoOrder{

        private final String idPedido;

        private final String descricaoPedido;

        private final BigDecimal totalPedido;

        /**
         * 
         * @param idPedido
         * @param descricaoPedido
         * @param totalPedido
         */
        public PagamentoOrder(String idPedido, String descricaoPedido, BigDecimal totalPedido) {
            this.idPedido = idPedido;
            this.descricaoPedido = descricaoPedido;
            this.totalPedido = totalPedido;
        }

        public String getIdPedido() {
            return idPedido;
        }

        public String getDescricaoPedido() {
            return descricaoPedido;
        }

        public BigDecimal getTotalPedido() {
            return totalPedido;
        }
    }
    
}
