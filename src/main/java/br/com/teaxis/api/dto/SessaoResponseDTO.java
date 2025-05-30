package br.com.teaxis.api.dto;

import br.com.teaxis.api.enums.StatusSessao;
import br.com.teaxis.api.enums.TipoAtendimento;
import br.com.teaxis.api.model.Sessao;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
    Long id,
    Long usuarioId,
    String nomeUsuario,
    Long profissionalId,
    String nomeProfissional,
    LocalDateTime dataHoraAgendamento,
    TipoAtendimento tipoAtendimento,
    String localOuLink,
    StatusSessao status,
    String observacoesUsuario,
    String observacoesProfissional,
    Integer duracaoEstimadaMinutos,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaModificacao
) {
   
    public SessaoResponseDTO(Sessao sessao) {
        this(
            sessao.getId(),
            sessao.getUsuario().getId(),
            sessao.getUsuario().getNome(),
            sessao.getProfissional().getId(),
            sessao.getProfissional().getUsuario().getNome(), 
            sessao.getDataHoraAgendamento(),
            sessao.getTipoAtendimento(),
            sessao.getLocalOuLink(),
            sessao.getStatus(),
            sessao.getObservacoesUsuario(),
            sessao.getObservacoesProfissional(),
            sessao.getDuracaoEstimadaMinutos(),
            sessao.getDataCriacao(),
            sessao.getDataUltimaModificacao()
        );
    }
}