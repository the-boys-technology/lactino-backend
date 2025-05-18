package br.com.tbt.lactino.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CadastrarFornecedorDTO (
        @NotBlank(message = "É obrigatório informar o nome do Fornecedor")
        String nome,
        @NotBlank(message = "É obrigatório informar o email do Fornecedor")
        String email,
        @NotBlank(message = "É obrigatório informar a localizacao do Fornecedor")
        String localizacao){
}
