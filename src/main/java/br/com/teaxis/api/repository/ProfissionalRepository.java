package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; 
import java.util.Set;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    /**
     * Busca profissionais que correspondam a PELO MENOS UM dos critérios de especialização
     * ou método, e que AINDA NÃO tenham sido sugeridos para um usuário específico.
     * Esta busca é muito mais eficiente que carregar todos os profissionais na memória.
     */
    @Query("SELECT p FROM Profissional p WHERE " +
           "(EXISTS (SELECT 1 FROM p.especializacoes e WHERE e IN :especializacoes) OR " +
           "EXISTS (SELECT 1 FROM p.metodosUtilizados m WHERE m IN :metodos)) AND " +
           "p.id NOT IN (SELECT m.profissional.id FROM Matching m WHERE m.usuario.id = :usuarioId)")
    List<Profissional> findProfissionaisCompativeis(
            @Param("especializacoes") Set<String> especializacoes,
            @Param("metodos") Set<String> metodos,
            @Param("usuarioId") Long usuarioId
    );

    Optional<Profissional> findByUsuarioId(Long usuarioId);
}