package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CadastrarClienteDTO(
        @NotBlank(message = "É obrigatório informar o nome do Cliente")
        String nome,
        @NotBlank(message = "É obrigatório informar o email do Cliente")
        String email,
        @NotBlank(message = "É obrigatório informar a localizacao do Cliente")
        String localizacao
) {
}
