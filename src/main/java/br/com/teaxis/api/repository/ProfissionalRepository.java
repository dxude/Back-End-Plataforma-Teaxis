package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByUsuarioId(Long usuarioId);

    /**
     * Busca profissionais que AINDA NÃO tenham sido sugeridos (Matching) para este usuário.
     * A filtragem por especialização (String) será feita no Service (Java) para simplificar.
     */
    @Query("SELECT p FROM Profissional p WHERE " +
           "p.id NOT IN (SELECT m.profissional.id FROM Matching m WHERE m.usuario.id = :usuarioId)")
    List<Profissional> findCandidatosParaMatching(@Param("usuarioId") Long usuarioId);
    
}