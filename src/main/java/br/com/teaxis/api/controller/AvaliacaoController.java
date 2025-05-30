package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosAvaliacaoDTO;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity criar(@RequestBody DadosAvaliacaoDTO dados, @AuthenticationPrincipal Usuario usuarioLogado) {
        var avaliacao = avaliacaoService.criarAvaliacao(dados, usuarioLogado);
        return ResponseEntity.ok(avaliacao);
    }
}