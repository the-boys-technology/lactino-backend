package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Insumo {
    private UUID id;
    private String nome;
    private CategoriaInsumoEnum categoria;
    private String unidadeMedida;
    private Double quantidadeTotal;
    private Double quantidadeMinima;
    private LocalDate validade;
    private BigDecimal preco;
    private String fornecedor;
    private StatusInsumoEnum status;
}
