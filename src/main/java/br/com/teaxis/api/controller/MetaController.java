package br.com.teaxis.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.teaxis.api.dto.MetaDTO;
import br.com.teaxis.api.model.Meta;
import br.com.teaxis.api.service.MetaService;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaService service;

    public MetaController(MetaService service) {
        this.service = service;
    }

    @PostMapping
    public Meta criar(@RequestBody MetaDTO dto) {
        return service.criarMeta(dto);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Meta> listar(@PathVariable Long idUsuario) {
        return service.listarPorUsuario(idUsuario);
    }

    @PutMapping("/{id}")
    public Meta atualizar(@PathVariable Long id, @RequestBody MetaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
