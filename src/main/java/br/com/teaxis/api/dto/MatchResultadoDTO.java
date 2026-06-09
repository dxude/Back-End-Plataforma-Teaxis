package br.com.teaxis.api.dto;

public record MatchResultadoDTO(
    Long id_profissional, 
    String nome, 
    String contexto_compativel
) {}