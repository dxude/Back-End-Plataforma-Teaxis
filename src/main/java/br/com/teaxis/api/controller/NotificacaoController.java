package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosNotificacao;
import br.com.teaxis.api.dto.NotificacaoResponseDTO;
import br.com.teaxis.api.service.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller REST para endpoints de Notificações.

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criar(@RequestBody @Valid DadosNotificacao dados) {
        return ResponseEntity.ok(service.criar(dados));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listar(usuarioId));
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarComoLida(id));
    }
}

