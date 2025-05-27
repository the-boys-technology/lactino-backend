package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.TransacaoDTO;
import br.com.tbt.lactino.controller.response.TransacaoResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Fornecedor;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.repository.ClienteRepository;
import br.com.tbt.lactino.repository.FornecedorRepository;
import br.com.tbt.lactino.repository.TransacaoRepository;
import br.com.tbt.lactino.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;

    @Override
    public List<TransacaoResponse> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream()
                .map(TransacaoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TransacaoResponse buscarTransacao(Long transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        return new TransacaoResponse(transacao);
    }

    @Override
    public TransacaoResponse registrarTransacao(TransacaoDTO transacaoDTO) {
        Cliente cliente = clienteRepository.findById(transacaoDTO.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não foi encontrado"));

        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(transacaoDTO.fornecedorId());

        Transacao transacao = Transacao.builder()
                .tipo(transacaoDTO.tipo())
                .data(transacaoDTO.data())
                .valorTotal(transacaoDTO.valorTotal())
                .formaPagamento(transacaoDTO.formaPagamento())
                .cliente(cliente)
                .fornecedor(fornecedor.orElse(null))
                .descricao(transacaoDTO.descricao())
                .build();

        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return new TransacaoResponse(transacaoSalva);
    }

    @Override
    public TransacaoResponse atualizarTransacao(Long transacaoId, TransacaoDTO transacaoDTO) {
        Transacao transacaoExistente = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        Cliente cliente = clienteRepository.findById(transacaoDTO.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não foi encontrado"));

        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(transacaoDTO.fornecedorId());

        transacaoExistente.setTipo(transacaoDTO.tipo());
        transacaoExistente.setData(transacaoDTO.data());
        transacaoExistente.setValorTotal(transacaoDTO.valorTotal());
        transacaoExistente.setFormaPagamento(transacaoDTO.formaPagamento());
        transacaoExistente.setCliente(cliente);
        transacaoExistente.setFornecedor(fornecedor.orElse(null));
        transacaoExistente.setDescricao(transacaoDTO.descricao());

        Transacao atualizada = transacaoRepository.save(transacaoExistente);

        return new TransacaoResponse(atualizada);
    }

    @Override
    public void removerTransacao(Long transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        transacaoRepository.delete(transacao);
    }

}