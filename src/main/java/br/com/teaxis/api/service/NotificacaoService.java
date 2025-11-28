package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosNotificacao;
import br.com.teaxis.api.dto.NotificacaoResponseDTO;
import br.com.teaxis.api.model.Notificacao;
import br.com.teaxis.api.repository.NotificacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository repo;

    //Cria uma nova notificação para um usuário.
    @Transactional
    public NotificacaoResponseDTO criar(DadosNotificacao dados) {
        Notificacao n = new Notificacao();
        n.setUsuarioId(dados.usuarioId());
        n.setMensagem(dados.mensagem());
        n.setLido(false);
        repo.save(n);
        return new NotificacaoResponseDTO(n);
    }

    //Lista todas as notificações de um usuário.
    public List<NotificacaoResponseDTO> listar(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream()
                .map(NotificacaoResponseDTO::new)
                .toList();
    }

    //Marca a notificação como lida.
    @Transactional
    public NotificacaoResponseDTO marcarComoLida(Long id) {
        Notificacao n = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));
        n.setLido(true);
        repo.save(n);
        return new NotificacaoResponseDTO(n);
    }
}


