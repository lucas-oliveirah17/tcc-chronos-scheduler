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

import br.com.barberscheduler.backend.dto.UsuarioRequestDTO;
import br.com.barberscheduler.backend.dto.UsuarioResponseDTO;
import br.com.barberscheduler.backend.dto.UsuarioUpdateDTO;
import br.com.barberscheduler.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(
            @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioCriado = usuarioService.criar(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        
        return ResponseEntity.ok(usuarios); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        
        return ResponseEntity.ok(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody UsuarioUpdateDTO dto) {
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(id, dto);
        
        return ResponseEntity.ok(usuarioAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){
        usuarioService.deletar(id);
        
        return ResponseEntity.noContent().build();
    }
}