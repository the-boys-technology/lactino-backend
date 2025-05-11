package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.MovimentacaoEstoqueResponse;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;
import br.com.tbt.lactino.model.MovimentacaoEstoque;
import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;
import br.com.tbt.lactino.repository.MovimentacaoEstoqueRepository;
import br.com.tbt.lactino.service.InsumoService;
import br.com.tbt.lactino.service.MovimentacaoEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoEstoqueServiceImpl implements MovimentacaoEstoqueService {
  private final InsumoService insumoService;
  private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

  @Override
  @Transactional
  public RegistrarMovimentacaoResponse registrarMovimentacao(RegistrarMovimentacaoDTO dto) {
    TipoMovimentacaoEstoque tipo;
    UUID idInsumo;

    try {
      tipo = TipoMovimentacaoEstoque.valueOf(dto.tipo());
    } catch (IllegalArgumentException ex) {
      String erro = String.format("O tipo de movimentação %s é inválido", ex);
      throw new RuntimeException(erro);
    }

    try {
      idInsumo = UUID.fromString(dto.idInsumo());
    } catch (IllegalArgumentException ex) {
      String erro = String.format("O ID de insumo %s é inválido", dto.idInsumo());
      throw new RuntimeException(erro);
    }

    MovimentacaoEstoque novaMovimentacaoEstoque = dto.toEntity();
    novaMovimentacaoEstoque.setTipo(tipo);
    novaMovimentacaoEstoque.setDataMovimentacao(LocalDateTime.now());
    novaMovimentacaoEstoque.setInsumo(this.insumoService.buscarInsumo(idInsumo));

    MovimentacaoEstoque movimentacaoEstoqueSalva =
        this.movimentacaoEstoqueRepository.save(novaMovimentacaoEstoque);
    this.insumoService.alterarQuantidade(idInsumo, dto.quantidade(), tipo);

    return RegistrarMovimentacaoResponse.builder()
        .id(movimentacaoEstoqueSalva.getId())
        .data(movimentacaoEstoqueSalva.getDataMovimentacao())
        .tipo(movimentacaoEstoqueSalva.getTipo().getDescricao())
        .build();
  }

  @Override
  public MovimentacaoEstoqueResponse buscarMovimentacao(String id) {
    MovimentacaoEstoque movimentacaoEstoque = fetchMovimentacao(id);
    return new MovimentacaoEstoqueResponse(movimentacaoEstoque);
  }

  private MovimentacaoEstoque fetchMovimentacao(String id) {
    UUID idMovimentacao;

    try {
      idMovimentacao = UUID.fromString(id);
    } catch (IllegalArgumentException ex) {
      String erro = String.format("O ID de movimentação %s é inválido", id);
      throw new RuntimeException(erro);
    }

    return this.movimentacaoEstoqueRepository
        .findById(idMovimentacao)
        .orElseThrow(
            () -> {
              String erro =
                  String.format("Não foi encontrada nenhuma movimentação com o ID %s", id);
              return new RuntimeException(erro);
            });
  }
}
