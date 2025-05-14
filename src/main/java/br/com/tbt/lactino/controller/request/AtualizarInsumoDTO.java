package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record AtualizarInsumoDTO(
    @FutureOrPresent(message = "A data de validade não pode ser uma data passada")
        LocalDate validade,
    @PositiveOrZero(message = "A quantidade mínima não pode ser negativa") Double quantidadeMinima,
    String status) {}
