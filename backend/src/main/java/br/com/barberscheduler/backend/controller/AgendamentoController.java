package br.com.barberscheduler.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import br.com.barberscheduler.backend.dto.AgendamentoDTO;
import br.com.barberscheduler.backend.dto.AgendamentoRequestDTO;
import br.com.barberscheduler.backend.service.AgendamentoService;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    
    private final AgendamentoService agendamentoService;
    
    public AgendamentoController (AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }
    
    @PostMapping
    public ResponseEntity<AgendamentoDTO> criar(
            @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoDTO agendamentoCriado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(
            @PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }
        
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
