package seu.pacote.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import seu.pacote.models.Meta;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    List<Meta> findByIdUsuario(Long idUsuario);
}
