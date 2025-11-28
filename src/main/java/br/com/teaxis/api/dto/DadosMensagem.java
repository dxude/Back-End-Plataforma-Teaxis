package br.com.teaxis.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//DTO usado para criar uma nova mensagem.

public record DadosMensagem(
        @NotNull Long remetenteId,
        @NotNull Long destinatarioId,
        @NotBlank String conteudo
) {}

