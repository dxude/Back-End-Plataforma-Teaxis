package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.EvolucaoDTO;
import br.com.teaxis.api.model.Evolucao;
import br.com.teaxis.api.model.Meta;
import br.com.teaxis.api.repository.EvolucaoRepository;
import br.com.teaxis.api.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EvolucaoService {

    private final EvolucaoRepository evolucaoRepository;
    private final MetaRepository metaRepository;

    public EvolucaoService(EvolucaoRepository evolucaoRepository, MetaRepository metaRepository) {
        this.evolucaoRepository = evolucaoRepository;
        this.metaRepository = metaRepository;
    }

    public Evolucao criarEvolucao(EvolucaoDTO dto) {
        Meta meta = metaRepository.findById(dto.getMetaId())
                .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

        Evolucao evolucao = new Evolucao();
        evolucao.setMeta(meta);

        if (dto.getDataRegistro() == null) {
            evolucao.setDataRegistro(LocalDate.now());
        } else {
            evolucao.setDataRegistro(dto.getDataRegistro());
        }

        evolucao.setProgresso(dto.getProgresso());
        evolucao.setComentario(dto.getComentario());

        return evolucaoRepository.save(evolucao);
    }

    public List<Evolucao> listarPorMeta(Long metaId) {
        return evolucaoRepository.findByMetaId(metaId);
    }
}