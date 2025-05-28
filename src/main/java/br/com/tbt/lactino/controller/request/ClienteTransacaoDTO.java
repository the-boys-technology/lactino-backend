package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ClienteTransacaoDTO(
        @NotBlank
        UUID id
) {
}
