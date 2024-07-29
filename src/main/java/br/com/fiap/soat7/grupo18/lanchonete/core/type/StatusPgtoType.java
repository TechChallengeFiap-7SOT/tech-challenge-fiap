package br.com.fiap.soat7.grupo18.lanchonete.core.type;

import java.util.Optional;
import java.util.stream.Stream;

public enum StatusPgtoType {

    AGUARDANDO(0),
    APROVADO(1),
    RECUSADO(2);

    private final int flowOrder;

    private StatusPgtoType(int flowOrder){
        this.flowOrder = flowOrder;
    }

    public int getFlowOrder(){
        return flowOrder;
    }

    public static StatusPgtoType parseByName(String name){
        return Stream.of(StatusPgtoType.values())
                    .filter(v -> name != null && v.name().equalsIgnoreCase(Optional.ofNullable(name).map(String::trim).orElse("")))
                    .findFirst()
                    .orElse(null);
    }
}
