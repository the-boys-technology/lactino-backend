package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MudarSenhaDTO(
    @NotBlank(message = "É obrigatório informar a senha atual") String senhaAtual,
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>/?-]).{8,}$",
            message =
                "A senha precisa ter, no mínimo, 8 caracteres, com pelo menos um número e um caractere especial")
        String novaSenha) {}
