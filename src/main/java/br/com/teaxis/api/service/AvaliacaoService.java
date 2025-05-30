package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosAvaliacaoDTO;
import br.com.teaxis.api.model.Avaliacao;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.AvaliacaoRepository;
import br.com.teaxis.api.repository.ProfissionalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Transactional 
    public Avaliacao criarAvaliacao(DadosAvaliacaoDTO dados, Usuario usuarioLogado) {
        Profissional profissional = profissionalRepository.findById(dados.profissionalId())
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado"));

        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setProfissional(profissional);
        novaAvaliacao.setUsuario(usuarioLogado);
        novaAvaliacao.setNota(dados.nota());
        novaAvaliacao.setComentario(dados.comentario());

        avaliacaoRepository.save(novaAvaliacao);

        atualizarMediaDoProfissional(profissional);

        return novaAvaliacao;
    }

    private void atualizarMediaDoProfissional(Profissional profissional) {
        var avaliacoes = avaliacaoRepository.findAllByProfissionalId(profissional.getId());
        double media = avaliacoes.stream().mapToDouble(Avaliacao::getNota).average().orElse(0.0);
        
        double mediaArredondada = Math.round(media * 10.0) / 10.0;

        profissional.setAvaliacaoMedia(mediaArredondada);
        profissionalRepository.save(profissional);
    }
}