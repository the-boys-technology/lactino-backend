package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetarSenhaDTO(
    @NotBlank(message = "É obrigatório informar o email")
        @Email(message = "O email informado deve ser válido")
        String email,
    @NotBlank(message = "É obrigatório informar o código enviado para o usuário") String codigo,
    String novaSenha) {}
