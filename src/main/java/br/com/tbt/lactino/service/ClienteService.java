package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.CadastrarClienteDTO;
import br.com.tbt.lactino.controller.response.ClienteResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Transacao;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    ClienteResponse cadastrarCliente(CadastrarClienteDTO dto);

    ClienteResponse buscarClientePorId(UUID id);

    List<ClienteResponse> buscarClientes();

    ClienteResponse atualizarCliente(UUID id, CadastrarClienteDTO dto);

    void criarTransacao(UUID clienteId, Transacao transacao);
}
