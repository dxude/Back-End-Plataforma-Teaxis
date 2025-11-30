package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Interface para acessar mensagens no banco de dados.

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByRemetenteIdOrDestinatarioId(Long remetenteId, Long destinatarioId);
}

