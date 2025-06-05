package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetarSenhaDTO(
    @NotBlank(message = "É obrigatório informar o email")
        @Email(message = "O email informado deve ser válido")
        String email,
    @NotBlank(message = "É obrigatório informar o código enviado para o usuário") String codigo,
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>/?-]).{8,}$",
            message =
                "A senha precisa ter, no mínimo, 8 caracteres, com pelo menos um número e um caractere especial")
        String novaSenha) {}
