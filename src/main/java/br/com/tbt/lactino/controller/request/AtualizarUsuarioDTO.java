package br.com.tbt.lactino.controller.request;

import org.springframework.web.multipart.MultipartFile;

public record AtualizarUsuarioDTO(
        String nome,
        byte[] fotoPerfil,
        String cep,
        String cidade,
        String estado,
        String rua,
        String bairro
) {
}