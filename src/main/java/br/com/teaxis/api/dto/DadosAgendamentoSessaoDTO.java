package br.com.teaxis.api.dto;

import br.com.teaxis.api.enums.TipoAtendimento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoSessaoDTO(
    @NotNull
    Long profissionalId,

    @NotNull
    @Future 
    LocalDateTime dataHoraAgendamento,

    @NotNull
    TipoAtendimento tipoAtendimento,

    String localOuLink, 
    String observacoesUsuario,
    Integer duracaoEstimadaMinutos
) {}