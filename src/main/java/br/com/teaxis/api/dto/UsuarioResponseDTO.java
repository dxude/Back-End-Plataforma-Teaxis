package br.com.teaxis.api.dto;

import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.model.TipoUsuario; 
import java.time.LocalDate;
import java.util.Set;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    TipoUsuario tipo, 
    LocalDate dataNascimento,
    String tipoNeurodivergencia,
    Set<String> hobbies
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTipo(), 
            usuario.getDataNascimento(),
            usuario.getTipoNeurodivergencia(),
            usuario.getHobbies()
        );
    }
}