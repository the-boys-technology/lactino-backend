package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.AtualizarInsumoDTO;
import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.request.InsumoFiltro;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.controller.response.InsumoResponse;
import br.com.tbt.lactino.model.Insumo;
import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;
import br.com.tbt.lactino.repository.InsumoRepository;
import br.com.tbt.lactino.repository.specifications.InsumoSpecification;
import br.com.tbt.lactino.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {
  private final InsumoRepository insumoRepository;

  @Override
  @Transactional
  public CadastrarInsumoResponse cadastrarInsumo(CadastrarInsumoDTO dto) {
    Insumo novoInsumo = dto.toEntity();
    CategoriaInsumoEnum categoria;

    try {
      categoria = CategoriaInsumoEnum.valueOf(dto.categoria().toUpperCase());
    } catch (IllegalArgumentException e) {
      String erro = String.format("A categoria %s é inválida", dto.categoria());
      throw new RuntimeException(erro);
    }
    novoInsumo.setCategoria(categoria);

    StatusInsumoEnum status =
        dto.quantidadeTotal() > 0 ? StatusInsumoEnum.ATIVO : StatusInsumoEnum.ESGOTADO;
    novoInsumo.setStatus(status);

    novoInsumo.setIsDeletado(Boolean.FALSE);

    Insumo insumoSalvo = this.insumoRepository.save(novoInsumo);
    return new CadastrarInsumoResponse(insumoSalvo.getId(), insumoSalvo.getNome());
  }

  @Override
  public InsumoResponse buscarPorId(String id) {
    Insumo insumo = this.fetchInsumoById(id);

    return new InsumoResponse(insumo);
  }

  @Override
  public void deletarInsumo(String id) {
    Insumo insumo = fetchInsumoById(id);

    insumo.setIsDeletado(Boolean.TRUE);
    this.insumoRepository.save(insumo);
  }

  private Insumo fetchInsumoById(String id) {
    UUID idInsumo;

    try {
      idInsumo = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      String erro = String.format("O ID %s é inválido", id);
      throw new RuntimeException(erro);
    }

    return this.insumoRepository
        .findById(idInsumo)
        .orElseThrow(
            () -> {
              String erro = String.format("Não foi encontrado nenhum insumo com o ID %s", id);
              return new RuntimeException(erro);
            });
  }

  @Override
  public Insumo buscarInsumo(UUID id) {
    return fetchInsumoById(id.toString());
  }

  @Override
  public void alterarQuantidade(
      UUID id, Double quantidadeAlterar, TipoMovimentacaoEstoque tipoAlteracao) {
    Insumo insumo = fetchInsumoById(id.toString());

    if (tipoAlteracao == TipoMovimentacaoEstoque.ENTRADA) {
      insumo.setQuantidadeTotal(insumo.getQuantidadeTotal() + quantidadeAlterar);
    } else if (tipoAlteracao == TipoMovimentacaoEstoque.CONSUMO) {
      insumo.setQuantidadeTotal(insumo.getQuantidadeTotal() - quantidadeAlterar);
    } else if (tipoAlteracao == TipoMovimentacaoEstoque.AJUSTE) {
      insumo.setQuantidadeTotal(quantidadeAlterar); // todo Validar se essa é realmente a lógica
    }

    this.insumoRepository.save(insumo);
  }

  @Override
  public Page<InsumoResponse> listarInsumos(InsumoFiltro filtro, Pageable pageable) {
    Specification<Insumo> spec = InsumoSpecification.comFiltros(filtro);
    Page<Insumo> respostaInsumos = this.insumoRepository.findAll(spec, pageable);

    return respostaInsumos.map(InsumoResponse::new);
  }

  @Override
  public void atualizarInsumo(String id, AtualizarInsumoDTO dto) {
    Insumo insumo = fetchInsumoById(id);

    if (dto.validade() != null) {
      insumo.setValidade(dto.validade());
    }

    if (dto.quantidadeMinima() != null) {
      insumo.setQuantidadeMinima(dto.quantidadeMinima());
    }

    if (dto.status() != null && !dto.status().isBlank()) {
      StatusInsumoEnum status;

      try {
        status = StatusInsumoEnum.valueOf(dto.status());
      } catch (IllegalArgumentException ex) {
        String erro = String.format("O status %s é inválido", dto.status());
        throw new RuntimeException(erro);
      }

      insumo.setStatus(status);
    }

    this.insumoRepository.save(insumo);
  }
}
