package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.UserRole;

public record RegistroDTO(
        String nome,
        String email,
        String senha,
        UserRole role) {

    public Usuario toEntity(){
        return new Usuario(this.nome, this.email, this.senha, this.role);
    }

}
