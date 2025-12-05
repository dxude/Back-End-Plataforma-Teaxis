package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.CadastroProfissionalDTO;
import br.com.teaxis.api.dto.DadosAtualizacaoPerfilProfissionalDTO;
import br.com.teaxis.api.dto.ProfissionalResponseDTO;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.service.AutenticacaoService;
import br.com.teaxis.api.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> cadastrar(@RequestBody CadastroProfissionalDTO dto, UriComponentsBuilder uriBuilder) {
        var novoProfissional = autenticacaoService.cadastrarProfissional(dto);
        var uri = uriBuilder.path("/api/v1/profissionais/{id}")
                .buildAndExpand(novoProfissional.id()).toUri();
        
        return ResponseEntity.created(uri).body(novoProfissional);
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listar() {
        var lista = profissionalService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id) {
        var profissionalDTO = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissionalDTO);
    }

    @PutMapping("/perfil")
    public ResponseEntity<ProfissionalResponseDTO> atualizarPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody DadosAtualizacaoPerfilProfissionalDTO dados) {
        
        var resposta = profissionalService.atualizarOuCriarPerfil(usuarioLogado, dados);
        return ResponseEntity.ok(resposta);
    }
}