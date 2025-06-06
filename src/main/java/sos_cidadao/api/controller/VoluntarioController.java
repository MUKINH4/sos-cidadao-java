package sos_cidadao.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import sos_cidadao.api.dto.VoluntarioDTO;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.service.VoluntarioService;

@RestController
@RequestMapping("/voluntarios")
@SecurityRequirement(name = "bearer")
public class VoluntarioController {

    public record VoluntarioFilter(String nome){}

    @Autowired
    private VoluntarioService voluntarioService;

    @GetMapping
    @Cacheable("voluntarios")
    @Operation(tags = "Voluntários", summary = "Listar todos os voluntários", description = "Endpoint para listar todos os voluntários")
    public ResponseEntity<List<Voluntario>> listarTodos() {
        List<Voluntario> voluntarios = voluntarioService.listarTodos();
        return ResponseEntity.ok(voluntarios);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "voluntarios", key = "#id")
    @Operation(tags = "Voluntários", summary = "Buscar voluntário por ID", description = "Endpoint para buscar um voluntário pelo ID")
    public ResponseEntity<Voluntario> buscarPorId(@PathVariable String id) {
        Optional<Voluntario> voluntario = voluntarioService.buscarPorId(id);
        return voluntario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "voluntarios", allEntries = true)
    @Operation(tags = "Voluntários", summary = "Atualizar um voluntário", description = "Endpoint para atualizar um voluntário pelo ID")
    public ResponseEntity<Voluntario> atualizarVoluntario(@PathVariable String id, @RequestBody @Valid VoluntarioDTO voluntarioAtualizado) {
        Voluntario voluntario = voluntarioService.atualizarVoluntario(id, voluntarioAtualizado);
        return ResponseEntity.ok(voluntario);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"voluntarios", "usuarios"}, allEntries = true)
    @Operation(tags = "Voluntários", summary = "Deletar um voluntário", description = "Endpoint para deletar um voluntário pelo ID")
    public ResponseEntity<Void> deletarVoluntario(@PathVariable String id) {
        voluntarioService.deletarVoluntario(id);
        return ResponseEntity.noContent().build();
    }
}
