package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.model.Insumo;
import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import br.com.tbt.lactino.repository.InsumoRepository;
import br.com.tbt.lactino.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    Insumo insumoSalvo = this.insumoRepository.save(novoInsumo);
    return new CadastrarInsumoResponse(insumoSalvo.getId(), insumoSalvo.getNome());
  }
}
