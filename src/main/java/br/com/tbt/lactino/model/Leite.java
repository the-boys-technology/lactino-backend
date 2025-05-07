package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "leites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String descricao;

    @Column(nullable = false)
    private LocalDate dataObtencao;

    @Column(nullable = false)
    private LocalDate dataValidade;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TurnoEnum turno;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusLeiteEnum status;

    private String finalidade;

    // refatorar para ser a entidade FORNECEDOR
    private String fornecedor;

    @PrePersist
    public void calcularDataValidade() {
        if (dataObtencao != null && dataValidade == null) {
            this.dataValidade = dataObtencao.plusDays(7);
        }
    }
}
