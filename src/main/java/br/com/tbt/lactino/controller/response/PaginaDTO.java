package br.com.tbt.lactino.controller.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaDTO<T>(
    List<T> content,
    Integer page,
    Integer size,
    Long totalElements,
    Integer totalPages,
    Boolean last) {

  public PaginaDTO(Page<T> page) {
    this(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isLast());
  }
}
