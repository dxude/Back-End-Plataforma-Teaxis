package br.com.teaxis.api.dto;

import br.com.teaxis.api.model.Notificacao;

//DTO de saída para retorno dos dados de Notificacao ao cliente.

public record NotificacaoResponseDTO(
        Long id,
        Long usuarioId,
        String mensagem,
        boolean lido
) {
    public NotificacaoResponseDTO(Notificacao n) {
        this(n.getId(), n.getUsuarioId(), n.getMensagem(), n.isLido());
    }
}


