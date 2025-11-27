package br.com.teaxis.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.teaxis.api.model.Meta; 

public interface MetaRepository extends JpaRepository<Meta, Long> {
    List<Meta> findByIdUsuario(Long idUsuario);
}