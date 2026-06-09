package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    // Busca a caixa de entrada do usuário
    List<Mensagem> findByDestinatarioEmailOrderByDataEnvioDesc(String email);
    
    // Busca as mensagens enviadas pelo usuário
    List<Mensagem> findByRemetenteEmailOrderByDataEnvioDesc(String email);
}