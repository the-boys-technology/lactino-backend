package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.CadastrarClienteDTO;
import br.com.tbt.lactino.controller.response.ClienteResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.repository.ClienteRepository;
import br.com.tbt.lactino.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponse cadastrarCliente(CadastrarClienteDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setLocalizacao(dto.localizacao());

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return new ClienteResponse(clienteSalvo);
    }

    @Override
    public ClienteResponse buscarClientePorId(UUID id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));;

        return new ClienteResponse(cliente);
    }

    @Override
    public List<ClienteResponse> buscarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponse atualizarCliente(UUID id, CadastrarClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setLocalizacao(dto.localizacao());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return new ClienteResponse(clienteAtualizado);
    }
}
