package br.com.teaxis.api.service;

import br.com.teaxis.api.model.Matching;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.MatchingRepository;
import br.com.teaxis.api.repository.ProfissionalRepository;
import br.com.teaxis.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchingService {
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProfissionalRepository profissionalRepository;
    @Autowired private MatchingRepository matchingRepository;

    private record ProfissionalComPontuacao(Profissional profissional, int pontuacao) {}

    public List<Matching> sugerirMatchesParaUsuario(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + usuarioId + " não encontrado."));

        String especializacaoAlvo = usuario.getTipoNeurodivergencia();
        Set<String> metodosAlvo = traduzirComunicacaoParaMetodos(usuario.getModoComunicacao());
        Set<String> hobbiesDoUsuario = usuario.getHobbies();


        List<Profissional> candidatos = profissionalRepository.findCandidatosParaMatching(usuarioId);

        List<ProfissionalComPontuacao> profissionaisPontuados = new ArrayList<>();

        for (Profissional p : candidatos) {
            int pontuacao = 0;

        
            if (contemTexto(p.getEspecializacoes(), especializacaoAlvo)) {
                pontuacao += 10;
            }

            for (String metodo : metodosAlvo) {
                if (contemTexto(p.getMetodosUtilizados(), metodo)) {
                    pontuacao += 5;
                }
            }

            for (String hobbyUsuario : hobbiesDoUsuario) {
                if (contemTexto(p.getHobbies(), hobbyUsuario)) {
                    pontuacao += 2; 
                }
            }
            
            if (pontuacao > 0) {
                profissionaisPontuados.add(new ProfissionalComPontuacao(p, pontuacao));
            }
        }

        profissionaisPontuados.sort((a, b) -> Integer.compare(b.pontuacao(), a.pontuacao()));

        List<Profissional> melhoresProfissionais = profissionaisPontuados.stream()
                .limit(5)
                .map(ProfissionalComPontuacao::profissional)
                .toList();

        return melhoresProfissionais.stream().map(profissional -> {
            Matching match = new Matching();
            match.setUsuario(usuario);
            match.setProfissional(profissional);
            match.setStatus("SUGERIDO");
            match.setDataSugestao(LocalDate.now());
            return matchingRepository.save(match);
        }).collect(Collectors.toList());
    }

    private boolean contemTexto(String fonte, String palavraChave) {
        if (fonte == null || palavraChave == null) return false;
        return fonte.toLowerCase().contains(palavraChave.toLowerCase());
    }

    private Set<String> traduzirComunicacaoParaMetodos(String modoComunicacao) {
        if (modoComunicacao == null || modoComunicacao.isBlank()) {
            return Set.of();
        }
        String texto = modoComunicacao.toLowerCase();
        if (texto.contains("pecs") || texto.contains("visual") || texto.contains("pictograma")) {
            return Set.of("PECS", "Comunicação Alternativa", "Visual");
        }
        if (texto.contains("verbal") || texto.contains("fala")) {
            return Set.of("Fonoaudiologia", "Fala");
        }
        return Set.of();
    }
}