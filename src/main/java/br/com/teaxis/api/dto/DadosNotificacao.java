package br.com.teaxis.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//DTO de entrada para criação de uma notificação.

public record DadosNotificacao(
        @NotNull Long usuarioId,
        @NotBlank String mensagem
) {}

