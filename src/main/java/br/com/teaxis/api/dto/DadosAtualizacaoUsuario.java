package br.com.teaxis.api.dto;

import java.util.Set;


public record DadosAtualizacaoUsuario(
        String nome,
        String cidade,
        String estado,
        Set<String> hobbies
) {}