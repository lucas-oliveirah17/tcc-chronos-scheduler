package br.com.barberscheduler.backend.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.barberscheduler.backend.model.Usuario;

@Service
public class TokenService {
    
    @Value("${jwt.secret}")
    private String secret; // Chave secreta
    
    @Value("${jwt.expiration.hours}")
    private int expirationHours; // Tempo de expiração do token
    
    private static final String ISSUER = "BarberSchedulerAPI"; // Emissor do Token
    
    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura usando a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            // Criação do token
            String token = JWT.create()
                    .withIssuer(ISSUER) // Emissor do token
                    .withSubject(usuario.getEmail()) // "O usuario do token
                    .withClaim("id", usuario.getId()) // Adiciona dados extras (claim)
                    .withClaim("perfil", usuario.getPerfil().toString())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm); // Assina o token
            
            return token;
            
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token JWT", exception);            
        }
    }
    
    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            
            String validacao = JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token) // Tenta verificar o token
                    .getSubject(); // Se for válido, retorna o subject (email)
            
            return validacao;
           
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
    
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(expirationHours) // Adiciona o tempo de expiração
                .toInstant(ZoneOffset.of("-03:00")); // Define o fuso horário
    }
}