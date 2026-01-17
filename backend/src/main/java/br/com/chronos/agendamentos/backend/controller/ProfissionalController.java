package br.com.chronos.agendamentos.backend.controller;

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

import br.com.chronos.agendamentos.backend.dto.ProfissionalRequestDTO;
import br.com.chronos.agendamentos.backend.dto.ProfissionalResponseDTO;
import br.com.chronos.agendamentos.backend.service.ProfissionalService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
@Tag(name = "profissionais")
public class ProfissionalController {
    
    private final ProfissionalService profissionalService;
    
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }
    
    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> criar(
            @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalResponseDTO profissionalCriado = profissionalService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listarTodos() {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(
            @PathVariable Long id) {
        ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissional);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalResponseDTO profissionalAtualizado = profissionalService.atualizar(id, dto);
        return ResponseEntity.ok(profissionalAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}