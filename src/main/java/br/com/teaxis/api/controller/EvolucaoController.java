package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.EvolucaoDTO;
import br.com.teaxis.api.model.Evolucao;
import br.com.teaxis.api.service.EvolucaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evolucoes")
public class EvolucaoController {

    private final EvolucaoService service;

    public EvolucaoController(EvolucaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Evolucao> criar(@RequestBody EvolucaoDTO dto) {
        return ResponseEntity.ok(service.criarEvolucao(dto));
    }

    @GetMapping("/meta/{metaId}")
    public ResponseEntity<List<Evolucao>> listarPorMeta(@PathVariable Long metaId) {
        return ResponseEntity.ok(service.listarPorMeta(metaId));
    }
}