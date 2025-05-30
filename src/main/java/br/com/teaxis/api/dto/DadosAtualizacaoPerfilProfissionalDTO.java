package br.com.teaxis.api.dto;

import java.util.Set;

public record DadosAtualizacaoPerfilProfissionalDTO(
    String disponibilidade,
    Set<String> certificacoes,
    Set<String> especializacoes,
    Set<String> metodosUtilizados,
    Set<String> hobbies
) {}