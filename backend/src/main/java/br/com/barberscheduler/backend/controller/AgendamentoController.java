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

import br.com.barberscheduler.backend.model.Agendamento;
import br.com.barberscheduler.backend.service.AgendamentoService;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;
    
    public AgendamentoController (AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }
    
    @PostMapping
    public ResponseEntity<Agendamento> criar(
            @RequestBody Agendamento agendamento) {
        Agendamento novoAgendamento = agendamentoService.criar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }
    
    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(
            @PathVariable Long id) {
        Agendamento agendamentoEncontrado = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamentoEncontrado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(
            @PathVariable Long id,
            @RequestBody Agendamento agendamento) {
        Agendamento agendamentoAtualizado = agendamentoService.atualizar(id, agendamento);
        return ResponseEntity.ok(agendamentoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
