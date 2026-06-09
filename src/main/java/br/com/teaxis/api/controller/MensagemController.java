package br.com.teaxis.api.controller;

import br.com.teaxis.api.model.Mensagem;
import br.com.teaxis.api.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository repository;

    // 1. Rota para Enviar Nova Mensagem (POST)
    @PostMapping
    public ResponseEntity<Mensagem> enviar(@RequestBody Mensagem mensagem) {
        return ResponseEntity.ok(repository.save(mensagem));
    }

    // 2. Rota para Listar Caixa de Entrada (GET)
    // 2. Rota para Listar Caixa de Entrada (GET)
    @GetMapping("/entrada")
    public ResponseEntity<List<Mensagem>> caixaEntrada(@RequestParam String email) {
        return ResponseEntity.ok(repository.findByDestinatarioEmailOrderByDataEnvioDesc(email));
    }

    // 3. Rota para Listar Enviadas (GET)
    @GetMapping("/enviadas")
    public ResponseEntity<List<Mensagem>> enviadas(@RequestParam String email) {
        return ResponseEntity.ok(repository.findByRemetenteEmailOrderByDataEnvioDesc(email));
    }
}