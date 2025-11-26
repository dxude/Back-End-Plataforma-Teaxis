package br.com.teaxis.api.dto;

import java.time.LocalDate;

public record EvolucaoDTO(
    Long metaId,
    LocalDate dataRegistro,
    Double progresso,
    String comentario
) {}