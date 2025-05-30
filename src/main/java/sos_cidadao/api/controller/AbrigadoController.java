package sos_cidadao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sos_cidadao.api.model.Abrigado;
import sos_cidadao.api.service.AbrigadoService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/abrigados")
public class AbrigadoController {

    public record AbrigadoFilter(String nome){}

    @Autowired
    private AbrigadoService abrigadoService;

    @PostMapping
    @CacheEvict(value = "abrigados", key = "#id")
    @Operation(summary = "Criar um novo abrigado", description = "Endpoint para criar um novo abrigado")
    public ResponseEntity<Abrigado> criarAbrigado(@RequestBody @Valid Abrigado abrigado) {
        Abrigado novoAbrigado = abrigadoService.criarAbrigado(abrigado);
        return ResponseEntity.ok(novoAbrigado);
    }

    @GetMapping
    @Cacheable("abrigados")
    @Operation(summary = "Listar todos os abrigados", description = "Endpoint para listar todos os abrigados")
    public ResponseEntity<List<Abrigado>> listarTodos() {
        List<Abrigado> abrigados = abrigadoService.listarTodos();
        return ResponseEntity.ok(abrigados);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "abrigado", key = "#id")
    @Operation(summary = "Buscar abrigado por ID", description = "Endpoint para buscar um abrigado pelo ID")
    public ResponseEntity<Abrigado> buscarPorId(@PathVariable String id) {
        Optional<Abrigado> abrigado = abrigadoService.buscarPorId(id);
        return abrigado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "abrigados", key = "#id")
    @Operation(summary = "Atualizar um abrigado", description = "Endpoint para atualizar um abrigado pelo ID")
    public ResponseEntity<Abrigado> atualizarAbrigado(@PathVariable String id, @RequestBody @Valid Abrigado abrigadoAtualizado) {
        Abrigado abrigado = abrigadoService.atualizarAbrigado(id, abrigadoAtualizado);
        return ResponseEntity.ok(abrigado);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "abrigados", key = "#id")
    @Operation(summary = "Deletar um abrigado", description = "Endpoint para deletar um abrigado pelo ID")
    public ResponseEntity<Void> deletarAbrigado(@PathVariable String id) {
        abrigadoService.deletarAbrigado(id);
        return ResponseEntity.noContent().build();
    }
}
