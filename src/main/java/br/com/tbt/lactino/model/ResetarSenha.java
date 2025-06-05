package br.com.tbt.lactino.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_resetar_senha")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResetarSenha {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String codigo;

  @Column(nullable = false)
  private LocalDateTime dataExpiracao;
}
