package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private LocalDateTime dataMovimentacao;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoMovimentacaoEstoque tipo;

  @Column(nullable = false)
  private Double quantidade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "insumo_id", nullable = false)
  private Insumo insumo;

  private String descricao; // todo Validar se a descrição é opcional ou não
}
