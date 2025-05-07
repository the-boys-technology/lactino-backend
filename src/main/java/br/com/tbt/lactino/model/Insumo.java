package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "insumos")
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

  @Column(nullable = false)
  private LocalDate validade;

  @Column(nullable = false)
  private BigDecimal preco;

  @Column(nullable = false)
  private String fornecedor;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusInsumoEnum status;
}
