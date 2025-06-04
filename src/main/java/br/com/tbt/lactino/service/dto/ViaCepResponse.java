package br.com.tbt.lactino.service.dto;

public record ViaCepResponse(
        String cep,
        String bairro,
        String logradouro,
        String localidade,
        String uf
) {
    public String rua() {
        return logradouro;
    }

    public String cidade() {
        return localidade;
    }

    public String estado() {
        return uf;
    }
}
