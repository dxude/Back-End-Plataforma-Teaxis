package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Avaliacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findAllByProfissionalId(Long profissionalId);
}