package seu.pacote.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import seu.pacote.dto.MetaDTO;
import seu.pacote.models.Meta;
import seu.pacote.services.MetaService;

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
