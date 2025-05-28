package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import sos_cidadao.api.dto.UsuarioResponseDTO;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/registrar")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UsuarioResponseDTO criar(@RequestBody @Valid Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        var usuarioSalvo = repository.save(usuario);
        return new UsuarioResponseDTO(usuarioSalvo.getId(), usuarioSalvo.getEmail(), usuarioSalvo.getRole());
    }

}