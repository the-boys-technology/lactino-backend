package br.com.tbt.lactino.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Fornecedor {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String localizacao;

        //@Column(nullable = false)
        //private List<Transacao> transacoes;
}
