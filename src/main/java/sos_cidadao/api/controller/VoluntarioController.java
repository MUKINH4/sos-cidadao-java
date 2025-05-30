package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.service.VoluntarioService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voluntarios")
public class VoluntarioController {

    public record VoluntarioFilter(String nome){}

    @Autowired
    private VoluntarioService voluntarioService;

    @PostMapping
    @Operation(summary = "Criar um novo voluntário", description = "Endpoint para criar um novo voluntário")
    public ResponseEntity<Voluntario> criarVoluntario(@RequestBody @Valid Voluntario voluntario) {
        Voluntario novoVoluntario = voluntarioService.criarVoluntario(voluntario);
        return ResponseEntity.ok(novoVoluntario);
    }

    @GetMapping
    @Cacheable("voluntarios")
    @Operation(summary = "Listar todos os voluntários", description = "Endpoint para listar todos os voluntários")
    public ResponseEntity<List<Voluntario>> listarTodos() {
        List<Voluntario> voluntarios = voluntarioService.listarTodos();
        return ResponseEntity.ok(voluntarios);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "voluntario", key = "#id")
    @Operation(summary = "Buscar voluntário por ID", description = "Endpoint para buscar um voluntário pelo ID")
    public ResponseEntity<Voluntario> buscarPorId(@PathVariable String id) {
        Optional<Voluntario> voluntario = voluntarioService.buscarPorId(id);
        return voluntario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "voluntario", key = "#id")
    @Operation(summary = "Atualizar um voluntário", description = "Endpoint para atualizar um voluntário pelo ID")
    public ResponseEntity<Voluntario> atualizarVoluntario(@PathVariable String id, @RequestBody @Valid Voluntario voluntarioAtualizado) {
        Voluntario voluntario = voluntarioService.atualizarVoluntario(id, voluntarioAtualizado);
        return ResponseEntity.ok(voluntario);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "voluntario", key = "#id")
    @Operation(summary = "Deletar um voluntário", description = "Endpoint para deletar um voluntário pelo ID")
    public ResponseEntity<Void> deletarVoluntario(@PathVariable String id) {
        voluntarioService.deletarVoluntario(id);
        return ResponseEntity.noContent().build();
    }
}
