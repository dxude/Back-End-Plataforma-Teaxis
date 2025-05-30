package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosAtualizacaoUsuario;
import br.com.teaxis.api.dto.DadosCadastroUsuario;
import br.com.teaxis.api.dto.UsuarioResponseDTO;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.cadastrarUsuario(dados);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioResponseDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> detalharPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuarioDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> detalharUsuarioLogado(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioLogado));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, dados);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}