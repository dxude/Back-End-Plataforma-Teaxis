package br.com.teaxis.api.controller;

import br.com.teaxis.api.dto.DadosAutenticacao;
import br.com.teaxis.api.dto.DadosTokenJWT;
import br.com.teaxis.api.dto.UsuarioResponseDTO;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.service.TokenService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        
        var usuario = (Usuario) authentication.getPrincipal();
        
    
        var tokenJWT = tokenService.gerarToken(usuario);


        var usuarioDTO = new UsuarioResponseDTO(usuario);


        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuarioDTO));
    }
}