package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByUsuarioIdOrderByDataHoraAgendamentoDesc(Long usuarioId);
    List<Sessao> findByProfissionalIdOrderByDataHoraAgendamentoDesc(Long profissionalId);
}