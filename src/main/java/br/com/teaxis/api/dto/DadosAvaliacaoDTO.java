package br.com.teaxis.api.dto;

public record DadosAvaliacaoDTO(
    Long profissionalId,
    double nota,
    String comentario
) {}