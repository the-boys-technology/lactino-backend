package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Usuario;

public record AtualizarUsuarioDTO(
        String nome,
        String cep,
        String cidade,
        String estado,
        String rua,
        String bairro
) {
}