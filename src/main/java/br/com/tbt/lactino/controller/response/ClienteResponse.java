package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Cliente;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ClienteResponse(
        UUID id,
        String nome,
        String email,
        String localizacao,
        List<ClienteTransacaoResponse> transacoes
) {
    public ClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLocalizacao(),
                cliente.getTransacoes().isEmpty()
                        ? List.of()
                        : cliente.getTransacoes().stream()
                        .map(ClienteTransacaoResponse::new)
                        .collect(Collectors.toList())
        );
    }
}
