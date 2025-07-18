package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "insumos")
@Where(clause = "is_deletado = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false) // todo Validar se o nome deve ser único na base
  private String nome;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CategoriaInsumoEnum categoria;

  @Column(nullable = false)
  private String unidadeMedida;

  @Column(nullable = false)
  private Double quantidadeTotal;

  @Column(nullable = false)
  private Double quantidadeMinima;

  private LocalDate validade;

  @Column(nullable = false)
  private BigDecimal preco;

  @Column(nullable = false)
  private String fornecedor;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusInsumoEnum status;

  @Column(nullable = false)
  private Boolean isDeletado;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public boolean isEmBaixoEstoque() {
    return this.quantidadeTotal
        < this.quantidadeMinima; // todo Validar se há outro cálculo para baixo estoque
  }
}
