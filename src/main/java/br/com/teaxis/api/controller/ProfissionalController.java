package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosAtualizacaoPerfilProfissionalDTO; 
import br.com.teaxis.api.dto.ProfissionalResponseDTO;
import br.com.teaxis.api.model.Usuario; 
import br.com.teaxis.api.service.ProfissionalService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.web.bind.annotation.*; 
import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    
    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listar() {
     
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> detalhar(@PathVariable Long id) {
        ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissional);
    }

    @PutMapping("/meu-perfil")
    public ResponseEntity<ProfissionalResponseDTO> atualizarOuCriarPerfilProfissional(
            @AuthenticationPrincipal Usuario usuarioLogado, 
            @RequestBody DadosAtualizacaoPerfilProfissionalDTO dados) { 

        ProfissionalResponseDTO perfilAtualizado = profissionalService.atualizarOuCriarPerfil(usuarioLogado, dados);
        return ResponseEntity.ok(perfilAtualizado);
    }
}