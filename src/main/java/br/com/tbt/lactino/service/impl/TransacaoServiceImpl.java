package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.TransacaoDTO;
import br.com.tbt.lactino.controller.response.TransacaoResponse;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.repository.TransacaoRepository;
import br.com.tbt.lactino.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

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
        Transacao transacao = Transacao.builder()
                .tipo(transacaoDTO.tipo())
                .data(transacaoDTO.data())
                .valorTotal(transacaoDTO.valorTotal())
                .formaPagamento(transacaoDTO.formaPagamento())
                .clienteId(transacaoDTO.clienteId())
                .fornecedorId(transacaoDTO.fornecedorId())
                .leiteId(transacaoDTO.leiteId())
                .laticinioId(transacaoDTO.laticinioId())
                .descricao(transacaoDTO.descricao())
                .build();

        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return new TransacaoResponse(transacaoSalva);
    }
}