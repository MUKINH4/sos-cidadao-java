package sos_cidadao.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import sos_cidadao.api.controller.AuthController.Token;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.model.enums.TipoUsuario;
import sos_cidadao.api.model.enums.UserRole;

@Service
public class TokenService {

    Instant expiresAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));

    Algorithm algorithm = Algorithm.HMAC256("secret123");

    public Token createToken(Usuario usuario){
        var jwt = JWT.create()
            .withSubject(usuario.getId())
            .withClaim("email", usuario.getEmail())
            .withClaim("role", usuario.getRole().toString())
            .withClaim("tipo", usuario.getTipo().toString())
            .withClaim("nome", usuario.getNome().toString())
            .withExpiresAt(expiresAt)
            .sign(algorithm);

        return new Token(jwt, usuario.getEmail());
    }

    public Usuario getUserFromToken(String token){
        var verifiedToken = JWT.require(algorithm).build().verify(token);

        return Usuario.builder()
                .id(verifiedToken.getSubject())
                .email(verifiedToken.getClaim("email").toString())
                .role(UserRole.valueOf(verifiedToken.getClaim("role").asString()))
                .nome(verifiedToken.getClaim("nome").toString())
                .tipo(TipoUsuario.valueOf(verifiedToken.getClaim("tipo").asString()))
                .build();
    }
}