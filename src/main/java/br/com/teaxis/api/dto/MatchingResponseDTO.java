package br.com.teaxis.api.dto;

import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import java.time.LocalDate;

public record MatchingResponseDTO(
    Long id,
    Usuario usuario, 
    Profissional profissional,
    String status,
    LocalDate dataSugestao
) {}