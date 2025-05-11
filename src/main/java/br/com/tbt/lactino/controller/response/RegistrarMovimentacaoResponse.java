package br.com.tbt.lactino.controller.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegistrarMovimentacaoResponse(UUID id, LocalDateTime data, String tipo) {}
