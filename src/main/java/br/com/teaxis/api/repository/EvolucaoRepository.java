package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Evolucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvolucaoRepository extends JpaRepository<Evolucao, Long> {
    List<Evolucao> findByMetaId(Long metaId);
}