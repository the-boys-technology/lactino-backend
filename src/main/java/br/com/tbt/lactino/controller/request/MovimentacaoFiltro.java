package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MovimentacaoFiltro(
    TipoMovimentacaoEstoque tipo, LocalDate dataInicial, LocalDate dataFinal, Long insumoID) {}
