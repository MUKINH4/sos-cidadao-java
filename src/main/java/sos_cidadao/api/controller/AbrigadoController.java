package sos_cidadao.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import sos_cidadao.api.model.Abrigado;
import sos_cidadao.api.service.AbrigadoService;

@RestController
@RequestMapping("/abrigados")
@SecurityRequirement(name = "bearer")
public class AbrigadoController {

    public record AbrigadoFilter(String nome){}

    @Autowired
    private AbrigadoService abrigadoService;

    @PostMapping
    @CacheEvict(value = {"abrigados", "abrigos"}, allEntries = true)
    @Operation(tags = "Abrigados", summary = "Criar um novo abrigado", description = "Endpoint para criar um novo abrigado")
    public ResponseEntity<Abrigado> criarAbrigado(@RequestBody @Valid Abrigado abrigado) {
        System.out.println("abrigo: " + abrigado.getAbrigo());
        Abrigado novoAbrigado = abrigadoService.criarAbrigado(abrigado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAbrigado);
    }

    @GetMapping
    @Cacheable("abrigados")
    @Operation(tags = "Abrigados", summary = "Listar todos os abrigados", description = "Endpoint para listar todos os abrigados")
    public ResponseEntity<List<Abrigado>> listarTodos() {
        List<Abrigado> abrigados = abrigadoService.listarTodos();
        return ResponseEntity.ok(abrigados);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "abrigados")
    @Operation(tags = "Abrigados", summary = "Buscar abrigado por ID", description = "Endpoint para buscar um abrigado pelo ID")
    public ResponseEntity<Abrigado> buscarPorId(@PathVariable String id) {
        Optional<Abrigado> abrigado = abrigadoService.buscarPorId(id);
        return abrigado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "abrigados", allEntries = true)
    @Operation(tags = "Abrigados", summary = "Atualizar um abrigado", description = "Endpoint para atualizar um abrigado pelo ID")
    public ResponseEntity<Abrigado> atualizarAbrigado(@PathVariable String id, @RequestBody @Valid Abrigado abrigadoAtualizado) {
        Abrigado abrigado = abrigadoService.atualizarAbrigado(id, abrigadoAtualizado);
        return ResponseEntity.ok(abrigado);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"abrigos", "abrigados"}, allEntries = true)
    @Operation(tags = "Abrigados", summary = "Deletar um abrigado", description = "Endpoint para deletar um abrigado pelo ID")
    public ResponseEntity<Void> deletarAbrigado(@PathVariable String id) {
        abrigadoService.deletarAbrigado(id);
        return ResponseEntity.noContent().build();
    }
}
