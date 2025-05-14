package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.CadastrarFornecedorDTO;
import br.com.tbt.lactino.controller.response.FornecedorResponse;

import java.util.List;
import java.util.UUID;

public interface FornecedorService {
    FornecedorResponse cadastrarFornecedor(CadastrarFornecedorDTO dto);

    FornecedorResponse buscarFornecedorPorId(UUID id);

    List<FornecedorResponse> buscarFornecedores();

    FornecedorResponse atualizarFornecedor(UUID id, CadastrarFornecedorDTO dto);
}
