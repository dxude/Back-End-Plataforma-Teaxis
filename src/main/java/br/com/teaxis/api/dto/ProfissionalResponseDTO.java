package br.com.teaxis.api.dto;

import br.com.teaxis.api.model.Profissional;
import java.util.Set;

public record ProfissionalResponseDTO(
    Long id,
    String nome,
    String email,
    Double avaliacaoMedia,
    Set<String> especializacoes,
    Set<String> metodosUtilizados
) {
    public ProfissionalResponseDTO(Profissional profissional) {
        this(
            profissional.getId(),
            profissional.getUsuario().getNome(), 
            profissional.getUsuario().getEmail(), 
            profissional.getAvaliacaoMedia(),
            profissional.getEspecializacoes(),
            profissional.getMetodosUtilizados()
        );
    }
}