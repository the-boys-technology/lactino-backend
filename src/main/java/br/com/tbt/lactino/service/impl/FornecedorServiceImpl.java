package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.CadastrarFornecedorDTO;
import br.com.tbt.lactino.controller.response.FornecedorResponse;
import br.com.tbt.lactino.model.Fornecedor;
import br.com.tbt.lactino.repository.FornecedorRepository;
import br.com.tbt.lactino.service.FornecedorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public FornecedorResponse cadastrarFornecedor(CadastrarFornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(dto.nome());
        fornecedor.setEmail(dto.email());
        fornecedor.setLocalizacao(dto.localizacao());

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return new FornecedorResponse(fornecedorSalvo);
    }

    @Override
    public FornecedorResponse buscarFornecedorPorId(UUID id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor com ID " + id + " não encontrado"));;

        return new FornecedorResponse(fornecedor);
    }

    @Override
    public List<FornecedorResponse> buscarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        return fornecedores.stream()
                .map(FornecedorResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public FornecedorResponse atualizarFornecedor(UUID id, CadastrarFornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor com ID " + id + " não encontrado"));

        fornecedor.setNome(dto.nome());
        fornecedor.setEmail(dto.email());
        fornecedor.setLocalizacao(dto.localizacao());

        Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedor);

        return new FornecedorResponse(fornecedorAtualizado);
    }
}
