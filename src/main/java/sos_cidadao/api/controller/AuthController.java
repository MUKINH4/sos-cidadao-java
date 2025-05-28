package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.service.TokenService;

public class AuthController {
    public record Token(String token, String email){}
    public record Credentials(String email, String password){}

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){

        var authentication = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        
        var usuario = (Usuario) authManager.authenticate(authentication).getPrincipal();

        return tokenService.createToken(usuario);
    }
}
