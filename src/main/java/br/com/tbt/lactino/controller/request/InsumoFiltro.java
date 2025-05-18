package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import lombok.Builder;

@Builder
public record InsumoFiltro(
    String nome, StatusInsumoEnum status, CategoriaInsumoEnum categoria, Boolean emBaixoEstoque) {}
