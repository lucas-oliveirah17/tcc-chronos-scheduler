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

import br.com.barberscheduler.backend.dto.ServicoDTO;
import br.com.barberscheduler.backend.dto.ServicoRequestDTO;
import br.com.barberscheduler.backend.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    
    private final ServicoService servicoService;
    
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }
    
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(
            @RequestBody ServicoRequestDTO dto) {
        ServicoDTO servicoCriado = servicoService.criar(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        
        return ResponseEntity.ok(servicos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(
            @PathVariable Long id) {
        ServicoDTO servico = servicoService.buscarPorId(id);
        
        return ResponseEntity.ok(servico);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody ServicoRequestDTO dto) {
        ServicoDTO servicoAtualizado = servicoService.atualizar(id, dto);
        
        return ResponseEntity.ok(servicoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        servicoService.deletar(id);
        
        return ResponseEntity.noContent().build();
    }
}