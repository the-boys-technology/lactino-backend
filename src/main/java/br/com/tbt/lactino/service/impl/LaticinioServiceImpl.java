package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;
import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.repository.LaticinioRepository;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.repository.specifications.LaticinioSpecification;
import br.com.tbt.lactino.service.LaticinioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LaticinioServiceImpl implements LaticinioService {

    private final LaticinioRepository laticinioRepository;
    private final LeiteRepository leiteRepository;

    public LaticinioServiceImpl(LaticinioRepository laticinioRepository, LeiteRepository leiteRepository) {
        this.laticinioRepository = laticinioRepository;
        this.leiteRepository = leiteRepository;
    }

    @Override
    public UUID salvarLaticinio(LaticinioDTO laticinioDTO) {
        Leite leite = leiteRepository.findById(laticinioDTO.leiteUtilizadoId())
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + laticinioDTO.leiteUtilizadoId() + " não encontrado."));

        Laticinio laticinio = laticinioDTO.toEntity(leite);
        laticinioRepository.save(laticinio);
        return laticinio.getId();
    }

    @Override
    public LaticinioDetalhadoResponse buscarLaticinio(UUID id) {
        Laticinio laticinio = laticinioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Laticínio com ID " + id + " não encontrado."));
        return new LaticinioDetalhadoResponse(laticinio);
    }

    @Override
    public List<LaticinioDetalhadoResponse> listarLaticinios(LaticinioFiltro filtro) {
        return laticinioRepository.findAll(LaticinioSpecification.filtrar(filtro))
                .stream()
                .map(LaticinioDetalhadoResponse::new)
                .toList();
    }

}
