package br.com.fiap.soat7.grupo18.lanchonete.external.infra.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedido_produto")
public class PedidoProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProdutoEntity produto;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "combo_num")
    private Integer comboNum;
}
