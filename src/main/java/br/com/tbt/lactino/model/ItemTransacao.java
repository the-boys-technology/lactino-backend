package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.CategoriaProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "itens_transacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;

    private UUID produtoId;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProduto categoria;

    @Column(name = "unidade_de_medida", nullable = false)
    private String unidadeDeMedida;
}
