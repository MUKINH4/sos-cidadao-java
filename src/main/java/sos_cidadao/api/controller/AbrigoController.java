package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import sos_cidadao.api.model.Abrigo;
import sos_cidadao.api.service.AbrigoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/abrigos")
@SecurityRequirement(name = "bearer")
public class AbrigoController {

    public record AbrigoFilter(String nome){}

    @Autowired
    private AbrigoService abrigoService;

    @PostMapping
    @CacheEvict(value = "abrigos", allEntries = true)
    @Operation(tags = "Abrigos", summary = "Criar um novo abrigo", description = "Endpoint para criar um novo abrigo")
    public ResponseEntity<Abrigo> criarAbrigo(@RequestBody @Valid Abrigo abrigo) {
        Abrigo novoAbrigo = abrigoService.criarAbrigo(abrigo);
        return ResponseEntity.ok(novoAbrigo);
    }

    @GetMapping
    @Cacheable("abrigos")
    @Operation(tags = "Abrigos", summary = "Listar todos os abrigos", description = "Endpoint para listar todos os abrigos")
    public ResponseEntity<List<Abrigo>> listarTodos() {
        List<Abrigo> abrigos = abrigoService.listarTodos();
        return ResponseEntity.ok(abrigos);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "abrigos", key = "#id")
    @Operation(tags = "Abrigos", summary = "Buscar abrigo por ID", description = "Endpoint para buscar um abrigo pelo ID")
    public ResponseEntity<Abrigo> buscarPorId(@PathVariable Long id) {
        Optional<Abrigo> abrigo = abrigoService.buscarPorId(id);
        return abrigo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "abrigos", allEntries = true)
    @Operation(tags = "Abrigos", summary = "Atualizar um abrigo", description = "Endpoint para atualizar um abrigo pelo ID")
    public ResponseEntity<Abrigo> atualizarAbrigo(@PathVariable Long id, @RequestBody @Valid Abrigo abrigoAtualizado) {
        Abrigo abrigo = abrigoService.atualizarAbrigo(id, abrigoAtualizado);
        return ResponseEntity.ok(abrigo);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "abrigos", allEntries = true)
    @Operation(tags = "Abrigos", summary = "Deletar um abrigo", description = "Endpoint para deletar um abrigo pelo ID")
    public ResponseEntity<Void> deletarAbrigo(@PathVariable Long id) {
        abrigoService.deletarAbrigo(id);
        return ResponseEntity.noContent().build();
    }
}
