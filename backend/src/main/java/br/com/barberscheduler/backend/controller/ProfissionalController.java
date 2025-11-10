package br.com.barberscheduler.backend.controller;

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

import java.util.List;

import br.com.barberscheduler.backend.dto.ProfissionalDTO;
import br.com.barberscheduler.backend.dto.ProfissionalRequestDTO;
import br.com.barberscheduler.backend.service.ProfissionalService;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {
    
    private final ProfissionalService profissionalService;
    
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }
    
    @PostMapping
    public ResponseEntity<ProfissionalDTO> criar(
            @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalDTO profissionalCriado = profissionalService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<ProfissionalDTO>> listarTodos() {
        List<ProfissionalDTO> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> buscarPorId(
            @PathVariable Long id) {
        ProfissionalDTO profissional = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissional);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalDTO profissionalAtualizado = profissionalService.atualizar(id, dto);
        return ResponseEntity.ok(profissionalAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}