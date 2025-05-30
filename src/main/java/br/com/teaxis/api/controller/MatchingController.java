package br.com.teaxis.api.controller;

import br.com.teaxis.api.model.Matching;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/sugerir")
    public ResponseEntity<List<Matching>> sugerirMatches(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<Matching> sugestoes = matchingService.sugerirMatchesParaUsuario(usuarioLogado.getId());
        return ResponseEntity.ok(sugestoes);
    }
}
