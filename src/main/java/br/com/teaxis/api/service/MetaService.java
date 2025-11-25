package seu.pacote.services;

import java.util.List;
import org.springframework.stereotype.Service;
import seu.pacote.dto.MetaDTO;
import seu.pacote.models.Meta;
import seu.pacote.repositories.MetaRepository;

@Service
public class MetaService {

    private final MetaRepository repository;

    public MetaService(MetaRepository repository) {
        this.repository = repository;
    }

    public Meta criarMeta(MetaDTO dto) {
        Meta meta = new Meta(
            dto.idUsuario(),
            dto.titulo(),
            dto.descricao(),
            dto.status()
        );
        return repository.save(meta);
    }

    public List<Meta> listarPorUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    public Meta atualizar(Long id, MetaDTO dto) {
        Meta meta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

        meta.setTitulo(dto.titulo());
        meta.setDescricao(dto.descricao());
        meta.setStatus(dto.status());

        return repository.save(meta);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
