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

       
        Set<String> especializacoesAlvo = Set.of(usuario.getTipoNeurodivergencia()); 
        Set<String> metodosAlvo = traduzirComunicacaoParaMetodos(usuario.getModoComunicacao()); 
        Set<String> hobbiesDoUsuario = usuario.getHobbies();

    
        List<Profissional> candidatos = profissionalRepository.findProfissionaisCompativeis(
                especializacoesAlvo,
                metodosAlvo,
                usuarioId
        );

        // Agora, damos uma nota para cada candidato com base no quão bem ele se encaixa.
        List<ProfissionalComPontuacao> profissionaisPontuados = new ArrayList<>();
        for (Profissional p : candidatos) {
            int pontuacao = 0;
            // Pontos por especialização compatível
            for (String esp : p.getEspecializacoes()) {
                if (especializacoesAlvo.contains(esp)) {
                    pontuacao += 10;
                }
            }
            for (String metodo : p.getMetodosUtilizados()) {
                if (metodosAlvo.contains(metodo)) {
                    pontuacao += 5;
                }
            }
            for (String hobby : p.getHobbies()) { 
                if (hobbiesDoUsuario.contains(hobby)) {
                    pontuacao += 1;
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

        // Para cada um dos melhores, criamos um registro de "Matching" e salvamos no banco.
        return melhoresProfissionais.stream().map(profissional -> {
            Matching match = new Matching();
            match.setUsuario(usuario);
            match.setProfissional(profissional);
            match.setStatus("SUGERIDO");
            match.setDataSugestao(LocalDate.now());
            return matchingRepository.save(match);
        }).collect(Collectors.toList());
    }

    private Set<String> traduzirComunicacaoParaMetodos(String modoComunicacao) {
        if (modoComunicacao == null || modoComunicacao.isBlank()) {
            return Set.of();
        }
        String texto = modoComunicacao.toLowerCase();
        if (texto.contains("pecs") || texto.contains("visual") || texto.contains("pictograma")) {
            return Set.of("PECS", "Comunicação Alternativa e Aumentativa", "Comunicação Visual");
        }
        if (texto.contains("verbal")) {
            return Set.of("Terapia da Fala");
        }
        return Set.of();
    }
}
