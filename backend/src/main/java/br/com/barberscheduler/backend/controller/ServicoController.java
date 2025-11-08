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

import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    private final ServicoService servicoService;
    
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }
    
    @PostMapping
    public ResponseEntity<Servico> criar(
            @RequestBody Servico servico) {
        Servico novoServico = servicoService.criar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }
    
    @GetMapping
    public ResponseEntity<List<Servico>> listarTodosServicos() {
        List<Servico> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(
            @PathVariable Long id) {
        Servico servicoEncontrado = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servicoEncontrado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(
            @PathVariable Long id, 
            @RequestBody Servico servico) {
        Servico servicoAtualizado = servicoService.atualizar(id, servico);
        return ResponseEntity.ok(servicoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(
            @PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}