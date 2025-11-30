package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Interface para acessar notificações no banco de dados.
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioId(Long usuarioId);
}

