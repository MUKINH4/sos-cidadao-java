package sos_cidadao.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sos_cidadao.api.controller.AuthController.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import sos_cidadao.api.dto.UsuarioRequestDTO;
import sos_cidadao.api.dto.UsuarioResponseDTO;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.service.TokenService;
import sos_cidadao.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping("/registrar")
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(value = "usuarios", allEntries = true)
    @Operation(tags = "Usuários", summary = "Cria um usuário")
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody @Valid UsuarioRequestDTO usuarioRequest){
        var usuarioResponse = usuarioService.registrarUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @GetMapping
    @Cacheable("usuarios")
    @Operation(security = @SecurityRequirement(name = "bearer"), tags = "Usuários", summary = "Lista todos os usuários")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "usuario", key = "#id")
    @Operation(security = @SecurityRequirement(name = "bearer"), tags = "Usuários", summary = "Busca um usuário pelo ID")
    public ResponseEntity<Optional<Usuario>> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "usuario", allEntries = true)
    @Operation(security = @SecurityRequirement(name = "bearer"), tags = "Usuários", summary = "Atualiza um usuário pelo ID")
    public ResponseEntity<Map<String, Token>> atualizarUsuario(@PathVariable String id, @RequestBody @Valid Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);
        Token novoToken = tokenService.createToken(usuario);
        Map<String, Token> response = new HashMap<>();
        response.put("token", novoToken);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "usuario", allEntries = true)
    @Operation(security = @SecurityRequirement(name = "bearer"), tags = "Usuários", summary = "Deleta um usuário pelo ID")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}