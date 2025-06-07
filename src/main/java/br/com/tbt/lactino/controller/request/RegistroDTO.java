package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegistroDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>/?-]).{8,}$",
                message = "A senha precisa ter, no mínimo, 8 caracteres, com pelo menos um número e um caractere especial"
        )
        String senha,
        String cep,
        String cidade,
        String estado,
        String rua,
        String bairro,
        @NotNull UserRole role
) {

    public Usuario toEntity(){
        return new Usuario(this.nome, this.email, this.senha, this.role, this.cep, this.cidade,
                this.estado, this.rua, this.bairro);
    }

}