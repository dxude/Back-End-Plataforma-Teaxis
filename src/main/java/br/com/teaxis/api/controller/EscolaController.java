package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.EscolaDTO;
import br.com.teaxis.api.model.Escola;
import br.com.teaxis.api.service.EscolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas") 
public class EscolaController {

    @Autowired
    private EscolaService escolaService;

    @PostMapping
    public ResponseEntity<Escola> criar(@RequestBody EscolaDTO dto) {
        Escola novaEscola = escolaService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEscola);
    }

    @GetMapping
    public ResponseEntity<List<Escola>> listar() {
        return ResponseEntity.ok(escolaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escola> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(escolaService.buscarPorId(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        escolaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}