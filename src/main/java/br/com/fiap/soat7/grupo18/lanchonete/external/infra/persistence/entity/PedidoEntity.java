package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPedidoType;
import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente")
    private ClienteEntity cliente;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "valor")
    private BigDecimal valor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedidoType status = StatusPedidoType.RECEBIDO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "statusPgto")
    private StatusPgtoType statusPgto = StatusPgtoType.AGUARDANDO;

    @Column(name = "id_transacao_pagamento")
    private String idTransacaoPagamento;

    @Builder.Default
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<PedidoProdutoEntity> produtos = new ArrayList<>();
}
