package br.com.teaxis.api.controller;

import br.com.teaxis.api.model.Matching;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    public static record ProfissionalSimplesDTO(
        Long id,
        String nome,
        String especializacao,
        String sub,
        String bio,
        List<String> localidades
    ) {}

    @PostMapping("/paciente/{id}")
    public ResponseEntity<?> gerarMatchingInteligente(@PathVariable Long id) {
        List<ProfissionalSimplesDTO> dtoResponse = new ArrayList<>();
        try {
            // CORRIGIDO: O serviço real agora retorna List<Profissional>
            List<Profissional> profissionaisOrdenados = matchingService.executarMatchingParaPaciente(id);
            
            for (Profissional p : profissionaisOrdenados) {
                dtoResponse.add(new ProfissionalSimplesDTO(
                    p.getId(),
                    p.getUsuario() != null ? p.getUsuario().getNome() : "Profissional",
                    p.getEspecializacoes(),
                    p.getMetodosUtilizados(),
                    p.getHobbies(),
                    new ArrayList<>()
                ));
            }
            return ResponseEntity.ok(dtoResponse);
            
        } catch (Exception e) {
            System.out.println("Aviso: IA offline. Retornando dados simulados.");
            // O plano de fuga simulado continua retornando List<Matching>, então extraímos aqui:
            List<Matching> mockMatches = matchingService.obterMatchesSimulados(id); 
            for (Matching m : mockMatches) {
                if (m.getProfissional() != null) {
                    Profissional p = m.getProfissional();
                    dtoResponse.add(new ProfissionalSimplesDTO(
                        p.getId(),
                        p.getUsuario() != null ? p.getUsuario().getNome() : "Profissional Simulador",
                        p.getEspecializacoes(),
                        p.getMetodosUtilizados(),
                        p.getHobbies(),
                        new ArrayList<>()
                    ));
                }
            }
            return ResponseEntity.ok(dtoResponse);
        }
    }
}