package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Cliente;

import java.util.UUID;

public record TransacaoClienteResponse(
        UUID id,
        String nome,
        String email,
        String localizacao
) {
    public TransacaoClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLocalizacao()
        );
    }
}
