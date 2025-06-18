package br.com.tbt.lactino.service.dto;

import lombok.Builder;

@Builder
public record EnviarEmailDTO(String to, String subject, String body) {}
