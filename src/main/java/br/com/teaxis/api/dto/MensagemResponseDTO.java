package br.com.teaxis.api.dto;

import br.com.teaxis.api.model.Mensagem;
import java.time.LocalDateTime;

//DTO usado para devolver dados de uma mensagem ao cliente.

public record MensagemResponseDTO(
        Long id,
        Long remetenteId,
        Long destinatarioId,
        String conteudo,
        LocalDateTime dataEnvio
) {
    public MensagemResponseDTO(Mensagem mensagem) {
        this(mensagem.getId(), mensagem.getRemetenteId(), mensagem.getDestinatarioId(),
             mensagem.getConteudo(), mensagem.getDataEnvio());
    }
}

