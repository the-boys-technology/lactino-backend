package br.com.tbt.lactino.controller.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CadastrarFornecedorResponse(UUID id, String nome) {
}
