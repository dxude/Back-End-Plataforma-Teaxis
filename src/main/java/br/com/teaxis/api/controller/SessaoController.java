package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosAgendamentoSessaoDTO;
import br.com.teaxis.api.dto.DadosAtualizacaoStatusSessaoDTO;
import br.com.teaxis.api.dto.SessaoResponseDTO;
import br.com.teaxis.api.model.Profissional; 
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.ProfissionalRepository; 
import br.com.teaxis.api.service.SessaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional; 

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @Autowired 
    private ProfissionalRepository profissionalRepository;

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> agendar(
            @RequestBody @Valid DadosAgendamentoSessaoDTO dados,
            @AuthenticationPrincipal Usuario usuarioLogado,
            UriComponentsBuilder uriBuilder) {
        SessaoResponseDTO sessaoAgendada = sessaoService.agendarSessao(usuarioLogado, dados);
        var uri = uriBuilder.path("/sessoes/{id}").buildAndExpand(sessaoAgendada.id()).toUri();
        return ResponseEntity.created(uri).body(sessaoAgendada);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<SessaoResponseDTO>> listarMinhasSessoesComoUsuario(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<SessaoResponseDTO> sessoes = sessaoService.listarSessoesPorUsuario(usuarioLogado.getId());
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/profissional/minhas")
    public ResponseEntity<List<SessaoResponseDTO>> listarMinhasSessoesComoProfissional(@AuthenticationPrincipal Usuario usuarioProfissionalLogado) {
        List<SessaoResponseDTO> sessoes = sessaoService.listarSessoesParaProfissionalLogado(usuarioProfissionalLogado);
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> detalharSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        SessaoResponseDTO sessao = sessaoService.buscarSessaoPorId(id);

        boolean isPacienteDaSessao = sessao.usuarioId().equals(usuarioLogado.getId());
        boolean isProfissionalDaSessao = false;

        
        Optional<Profissional> perfilDoUsuarioLogado = profissionalRepository.findByUsuarioId(usuarioLogado.getId());
        if (perfilDoUsuarioLogado.isPresent()) {
            isProfissionalDaSessao = sessao.profissionalId().equals(perfilDoUsuarioLogado.get().getId());
        }

        if (!isPacienteDaSessao && !isProfissionalDaSessao) {
             throw new SecurityException("Usuário não autorizado a visualizar esta sessão.");
             
        }

        return ResponseEntity.ok(sessao);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SessaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoStatusSessaoDTO dados,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        SessaoResponseDTO sessaoAtualizada = sessaoService.atualizarStatusSessao(id, dados, usuarioLogado);
        return ResponseEntity.ok(sessaoAtualizada);
    }
}