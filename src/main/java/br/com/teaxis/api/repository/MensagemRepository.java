package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByDestinatarioEmailOrderByDataEnvioDesc(String email);
    List<Mensagem> findByRemetenteEmailOrderByDataEnvioDesc(String email);
    
    // 🌟 Adicione esta linha para o Service do seu grupo parar de chorar erro:
    List<Mensagem> findByRemetenteIdOrDestinatarioId(Long remetenteId, Long destinatarioId);
}