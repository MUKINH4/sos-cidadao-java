package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.service.TokenService;

@RestController
@RequestMapping
public class AuthController {
    public record Token(String token, String email){}
    public record Credentials(String email, String senha){}

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    @Operation(tags = "Autenticação", summary = "Realiza o login do usuário", description = "Endpoint para autenticar um usuário e gerar um token JWT")
    public Token login(@RequestBody Credentials credentials){

        var authentication = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.senha());
        
        var usuario = (Usuario) authManager.authenticate(authentication).getPrincipal();

        return tokenService.createToken(usuario);
    }
}
