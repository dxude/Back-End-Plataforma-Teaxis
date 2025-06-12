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
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
    var authentication = manager.authenticate(authenticationToken);

    // Pega o objeto Usuario completo que foi autenticado
    var usuario = (Usuario) authentication.getPrincipal();
    
    // Gera o token para esse usuário
    var tokenJWT = tokenService.gerarToken(usuario);

    // Cria um DTO com os dados públicos do usuário
    var usuarioDTO = new UsuarioResponseDTO(usuario);

    // Retorna uma resposta contendo o TOKEN e os DADOS DO USUÁRIO
    return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuarioDTO));
}
}