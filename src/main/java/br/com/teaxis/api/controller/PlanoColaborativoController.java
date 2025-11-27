package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.PlanoColaborativoDTO;
import br.com.teaxis.api.model.PlanoColaborativo;
import br.com.teaxis.api.service.PlanoColaborativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos-colaborativos")
public class PlanoColaborativoController {

    @Autowired
    private PlanoColaborativoService planoService;

    @PostMapping
    public ResponseEntity<PlanoColaborativo> criar(@RequestBody PlanoColaborativoDTO dto) {
        PlanoColaborativo novoPlano = planoService.criarPlano(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlano);
    }

    @GetMapping
    public ResponseEntity<List<PlanoColaborativo>> listar() {
        return ResponseEntity.ok(planoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoColaborativo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }
}