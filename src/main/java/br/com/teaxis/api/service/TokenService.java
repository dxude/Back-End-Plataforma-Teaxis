package br.com.teaxis.api.service;

import br.com.teaxis.api.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Teaxis")
                    .withSubject(usuario.getEmail()) 
                    .withClaim("id", usuario.getId()) 
                    .withExpiresAt(dataExpiracao()) 
                    .sign(algoritmo); 
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT para o usuário: " + usuario.getEmail(), exception);
        }
    }

    
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Teaxis") 
                    .build()
                    .verify(tokenJWT) 
                    .getSubject(); 
        } catch (JWTVerificationException exception) {
            
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
    // Pega o momento ATUAL em tempo universal (UTC) e adiciona 2 horas.
    // Simples, direto e à prova de erros de fuso horário.
    return Instant.now().plus(2, ChronoUnit.HOURS);
}
}