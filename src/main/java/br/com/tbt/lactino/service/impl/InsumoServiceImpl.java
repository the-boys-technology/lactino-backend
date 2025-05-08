package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.controller.response.InsumoResponse;
import br.com.tbt.lactino.model.Insumo;
import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import br.com.tbt.lactino.repository.InsumoRepository;
import br.com.tbt.lactino.service.InsumoService;
import lombok.RequiredArgsConstructor;
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
}
