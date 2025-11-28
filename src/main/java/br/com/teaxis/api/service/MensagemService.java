package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosMensagem;
import br.com.teaxis.api.dto.MensagemResponseDTO;
import br.com.teaxis.api.model.Mensagem;
import br.com.teaxis.api.repository.MensagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//Service responsável pela lógica de negócio de Mensagens.

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Transactional
    public MensagemResponseDTO enviarMensagem(DadosMensagem dados) {
        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setRemetenteId(dados.remetenteId());
        novaMensagem.setDestinatarioId(dados.destinatarioId());
        novaMensagem.setConteudo(dados.conteudo());
        novaMensagem.setDataEnvio(LocalDateTime.now());

        mensagemRepository.save(novaMensagem);
        return new MensagemResponseDTO(novaMensagem);
    }

    public List<MensagemResponseDTO> listarMensagens(Long usuarioId) {
        List<Mensagem> mensagens = mensagemRepository.findByRemetenteIdOrDestinatarioId(usuarioId, usuarioId);

        if (mensagens.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma mensagem encontrada para o usuário: " + usuarioId);
        }

        return mensagens.stream()
                .map(MensagemResponseDTO::new)
                .collect(Collectors.toList());
    }
}




