package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "laticinios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laticinio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tipo_produto", nullable = false)
    private String tipoProduto;

    @Column(nullable = false)
    private BigDecimal quantidadeProduzida;

    @Column(nullable = false)
    private LocalDate dataProducao;

    @Column(nullable = false)
    private LocalDate dataValidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "leite_utilizado_id", nullable = false)
    private Leite leiteUtilizado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusLaticinioEnum status;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    public void calcularValidade() {
        if (dataValidade == null) {
            switch (tipoProduto.toLowerCase()) {
                case "queijo minas" -> this.dataValidade = dataProducao.plusDays(10);
                case "iogurte" -> this.dataValidade = dataProducao.plusDays(7);
                case "manteiga" -> this.dataValidade = dataProducao.plusDays(30);
                default -> this.dataValidade = dataProducao.plusDays(5);
            }
        }
    }

    public Laticinio(String tipoProduto, BigDecimal quantidadeProduzida, LocalDate dataProducao, String descricao, StatusLaticinioEnum status, Leite leiteUtilizado, Usuario usuario) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProduzida = quantidadeProduzida;
        this.dataProducao = dataProducao;
        this.descricao = descricao;
        this.status = status;
        this.leiteUtilizado = leiteUtilizado;
        this.usuario = usuario;
    }
}
