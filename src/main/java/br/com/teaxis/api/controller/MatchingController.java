package br.com.teaxis.api.controller;

import br.com.teaxis.api.model.Matching;
import br.com.teaxis.api.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/paciente/{id}")
    public ResponseEntity<?> gerarMatchingInteligente(@PathVariable Long id) {
        try {
            // AJUSTADO: Agora recebe a List<Matching> que a IA real salva e retorna
            List<Matching> matches = matchingService.executarMatchingParaPaciente(id);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            // PLANO DE FUGA: Se a IA falhar, o Python estiver offline ou faltar campos, o front NÃO quebra!
            System.out.println("Aviso: IA offline ou sem dados estruturados. Retornando dados simulados para o Front-end.");
            
            // AJUSTADO: Retorna a lista simulada baseada nas entidades do H2
            List<Matching> mockMatches = matchingService.obterMatchesSimulados(id); 
            return ResponseEntity.ok(mockMatches);
        }
    }
}