package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Fornecedor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record FornecedorResponse(
        UUID id,
        String nome,
        String email,
        String localizacao,
        List<FornecedorTransacaoResponse> transacoes
) {
    public FornecedorResponse(Fornecedor fornecedor) {
        this(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getLocalizacao(),
                fornecedor.getTransacoes().isEmpty()
                        ? List.of()
                        : fornecedor.getTransacoes().stream()
                        .map(FornecedorTransacaoResponse::new)
                        .collect(Collectors.toList())
        );
    }
}
