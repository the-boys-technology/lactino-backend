package br.com.tbt.lactino.service;

import br.com.tbt.lactino.service.dto.ViaCepResponse;

public interface EnderecoService {
    ViaCepResponse buscarEnderecorPorCep(String cep);
}
