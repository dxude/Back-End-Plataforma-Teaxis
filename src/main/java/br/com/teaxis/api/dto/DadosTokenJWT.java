package br.com.teaxis.api.dto;

// Adicionamos o UsuarioResponseDTO aqui para incluir os dados do usuário na resposta
public record DadosTokenJWT(String token, UsuarioResponseDTO usuario) {
}