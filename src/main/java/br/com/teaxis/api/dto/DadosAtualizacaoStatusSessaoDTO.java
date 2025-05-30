package br.com.teaxis.api.dto;

import br.com.teaxis.api.enums.StatusSessao;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoStatusSessaoDTO(
    @NotNull
    StatusSessao novoStatus,
    String observacoes 
) {}