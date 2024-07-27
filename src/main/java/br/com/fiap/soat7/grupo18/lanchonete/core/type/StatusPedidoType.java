package br.com.fiap.soat7.grupo18.lanchonete.core.type;

import java.util.Optional;
import java.util.stream.Stream;

public enum StatusPedidoType {

    CANCELADO(-1, false),
    RECEBIDO(0, false),
    EM_PREPARACAO(1, true),
    PRONTO(2, true),
    FINALIZADO(3, true);
    
    
    private final int flowOrder;
    private final boolean paymentRequired;

    /**
     * 
     * @param flowOrder
     * @param paymentRequired
     */
    private StatusPedidoType(int flowOrder, boolean paymentRequired){
        this.flowOrder = flowOrder;
        this.paymentRequired = paymentRequired;
    }

    public int getFlowOrder(){
        return flowOrder;
    }

    public boolean isPaymentRequired(){
        return paymentRequired;
    }

    public static StatusPedidoType parseByName(String name){
        return Stream.of(StatusPedidoType.values())
                    .filter(v -> v.name().equalsIgnoreCase(Optional.ofNullable(name).map(String::trim).orElse("")))
                    .findFirst()
                    .orElse(null);
    }
}
