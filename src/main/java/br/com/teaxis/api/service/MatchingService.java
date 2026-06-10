package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.EspecialistaIdexarDTO;
import br.com.teaxis.api.dto.MatchResultadoDTO;
import br.com.teaxis.api.dto.PacienteMatchRequestDTO;
import br.com.teaxis.api.model.Matching;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.MatchingRepository;
import br.com.teaxis.api.repository.ProfissionalRepository;
import br.com.teaxis.api.repository.UsuarioRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private final WebClient webClient;
    private final UsuarioRepository usuarioRepository;
    private final ProfissionalRepository profissionalRepository;
    private final MatchingRepository matchingRepository;

    public MatchingService(WebClient.Builder webClientBuilder, 
                           UsuarioRepository usuarioRepository, 
                           ProfissionalRepository profissionalRepository, 
                           MatchingRepository matchingRepository) {
        
        this.webClient = webClientBuilder.baseUrl("https://5vxp627d-8000.brs.devtunnels.ms").build();
        this.usuarioRepository = usuarioRepository;
        this.profissionalRepository = profissionalRepository;
        this.matchingRepository = matchingRepository;
    }

    public void indexarProfissionalNoBancoVetorial(Profissional profissional) {
        String textoClinico = String.format(
            "Profissional: %s. Especializações: %s. Métodos e abordagens utilizadas: %s. Hobbies pessoais: %s.",
            profissional.getUsuario().getNome(),
            profissional.getEspecializacoes(),
            profissional.getMetodosUtilizados(),
            profissional.getHobbies()
        );

        EspecialistaIdexarDTO dto = new EspecialistaIdexarDTO(
            profissional.getId(), 
            profissional.getUsuario().getNome(), 
            textoClinico
        );

        this.webClient.post()
                .uri("/indexar-profissional")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block(); 
    }

    public List<Profissional> executarMatchingParaPaciente(Long pacienteId) {
        Usuario paciente = usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        String perfilConsolidado = String.format(
            "Paciente com diagnóstico de %s, gênero %s, residente em %s-%s. " +
            "Preferências Sensoriais: %s. Modo de Comunicação preferencial: %s. Hobbies e interesses: %s.",
            paciente.getTipoNeurodivergencia(),
            paciente.getGenero(),
            paciente.getCidade(),
            paciente.getEstado(),
            paciente.getPreferenciasSensoriais(),
            paciente.getModoComunicacao(),
            paciente.getHobbies() != null ? String.join(", ", paciente.getHobbies()) : "Nenhum"
        );

        PacienteMatchRequestDTO requestDto = new PacienteMatchRequestDTO(perfilConsolidado);

        List<MatchResultadoDTO> resultadosIa = this.webClient.post()
                .uri("/buscar-match")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MatchResultadoDTO>>() {})
                .block();

        List<Profissional> profissionaisOrdenados = new ArrayList<>();

        if (resultadosIa != null) {
            for (MatchResultadoDTO item : resultadosIa) {
                Profissional prof = profissionalRepository.findById(item.id_profissional()).orElse(null);
                if (prof != null) {
                    profissionaisOrdenados.add(prof);
                }
            }
        }
        return profissionaisOrdenados;
    }

    public List<Matching> obterMatchesSimulados(Long pacienteId) {
        Usuario paciente = usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        List<Profissional> profissionais = profissionalRepository.findAll();
        java.util.concurrent.atomic.AtomicLong idContador = new java.util.concurrent.atomic.AtomicLong(1);

        return profissionais.stream()
                .map(prof -> {
                    Matching m = new Matching();
                    m.setId(idContador.getAndIncrement());
                    m.setUsuario(paciente);
                    m.setProfissional(prof);
                    m.setStatus("SIMULADO");
                    m.setDataSugestao(LocalDate.now());
                    return m;
                })
                .collect(Collectors.toList());
    }
}