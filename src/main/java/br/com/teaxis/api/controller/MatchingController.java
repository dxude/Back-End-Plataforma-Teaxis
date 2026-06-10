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

    /**
     * DTO plano que reflete exatamente a estrutura de propriedades 
     * que o componente React (BuscarEspecialistas.jsx) consome nos cards.
     */
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
            // 1. Chama o serviço da IA que retorna a lista de profissionais ordenados por relevância
            List<Profissional> profissionaisOrdenados = matchingService.executarMatchingParaPaciente(id);
            
            if (profissionaisOrdenados != null) {
                for (Profissional p : profissionaisOrdenados) {
                    if (p != null) {
                        // Tratamento de segurança contra campos nulos para não quebrar o mapeamento no Front
                        String nomeProfissional = (p.getUsuario() != null) ? p.getUsuario().getNome() : "Especialista TEAxis";
                        String especializacao = (p.getEspecializacoes() != null) ? p.getEspecializacoes() : "Geral";
                        String metodos = (p.getMetodosUtilizados() != null) ? p.getMetodosUtilizados() : "Abordagens Integradas";
                        String hobbies = (p.getHobbies() != null) ? p.getHobbies() : "Sem biografia disponível";

                        dtoResponse.add(new ProfissionalSimplesDTO(
                            p.getId(),
                            nomeProfissional,
                            especializacao,
                            metodos,
                            hobbies,
                            new ArrayList<>() // Evita que o 'item.localidades.map' estoure erro no JavaScript se estiver vazio
                        ));
                    }
                }
            }
            return ResponseEntity.ok(dtoResponse);
            
        } catch (Exception e) {
            System.out.println("Aviso: IA offline ou erro na rota principal. Acionando dados simulados de contingência.");
            
            try {
                // 2. Plano de fuga: se o Python/DevTunnel falhar, o Front ainda renderiza os dados do banco H2/Neon
                List<Matching> mockMatches = matchingService.obterMatchesSimulados(id); 
                
                if (mockMatches != null) {
                    for (Matching m : mockMatches) {
                        if (m != null && m.getProfissional() != null) {
                            Profissional p = m.getProfissional();
                            String nomeProfissional = (p.getUsuario() != null) ? p.getUsuario().getNome() : "Especialista Simulador";
                            
                            dtoResponse.add(new ProfissionalSimplesDTO(
                                p.getId(),
                                nomeProfissional,
                                p.getEspecializacoes() != null ? p.getEspecializacoes() : "Simulado",
                                p.getMetodosUtilizados() != null ? p.getMetodosUtilizados() : "Método",
                                p.getHobbies() != null ? p.getHobbies() : "Biografia",
                                new ArrayList<>()
                            ));
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Erro crítico ao gerar os dados simulados.");
            }
            
            return ResponseEntity.ok(dtoResponse);
        }
    }


    @Autowired
private br.com.teaxis.api.repository.ProfissionalRepository profissionalRepository;

@PostMapping("/sincronizar-ia")
public ResponseEntity<String> sincronizarBancoVetorial() {
    try {
        List<Profissional> todos = profissionalRepository.findAll();
        for (Profissional p : todos) {
            matchingService.indexarProfissionalNoBancoVetorial(p);
        }
        return ResponseEntity.ok("Sincronizados " + todos.size() + " profissionais na IA local com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Erro ao sincronizar: " + e.getMessage());
    }
}
}