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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // refatorar para ser a entidade FORNECEDOR
    private String fornecedor;

    public Leite(String nome, String descricao, LocalDate dataObtencao, String origem, TurnoEnum turno, StatusLeiteEnum status, String finalidade, String fornecedor, Usuario usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataObtencao = dataObtencao;
        this.dataValidade = this.calcularDataValidade();
        this.origem = origem;
        this.turno = turno;
        this.status = status;
        this.finalidade = finalidade;
        this.fornecedor = fornecedor;
        this.usuario = usuario;
    }

    public LocalDate calcularDataValidade() {
        if (dataObtencao != null && dataValidade == null) {
           return dataObtencao.plusDays(7);
        }
        return null;
    }
}
