package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosMensagem;
import br.com.teaxis.api.dto.MensagemResponseDTO;
import br.com.teaxis.api.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Controller REST para endpoints de Mensagens.

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService service;

    @PostMapping
    public ResponseEntity<MensagemResponseDTO> enviar(@RequestBody @Valid DadosMensagem dados) {
        return ResponseEntity.ok(service.enviarMensagem(dados));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<MensagemResponseDTO>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarMensagens(usuarioId));
    }
}

