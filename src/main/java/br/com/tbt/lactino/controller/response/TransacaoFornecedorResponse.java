package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Fornecedor;

import java.util.UUID;

public record TransacaoFornecedorResponse(
        UUID id,
        String nome,
        String email,
        String localizacao
) {
    public TransacaoFornecedorResponse(Fornecedor fornecedor) {
        this(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getLocalizacao()
        );
    }
}
