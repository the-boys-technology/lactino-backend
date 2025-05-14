package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Fornecedor;

import java.util.UUID;

public record FornecedorResponse(
        UUID id,
        String nome,
        String email,
        String localizacao
        //List<Transacao> transacoes
) {
    public FornecedorResponse(Fornecedor fornecedor) {
        this(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getEmail(),
                fornecedor.getLocalizacao());
                //fornecedor.getTransacoes());
    }
}
