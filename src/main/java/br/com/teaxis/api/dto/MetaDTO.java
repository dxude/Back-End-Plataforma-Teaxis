package br.com.teaxis.api.dto;

public record MetaDTO(
    Long idUsuario,
    String titulo,
    String descricao,
    String status
) {}
