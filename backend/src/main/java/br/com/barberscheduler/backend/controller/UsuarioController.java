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

import br.com.barberscheduler.backend.dto.UsuarioCreateDTO;
import br.com.barberscheduler.backend.dto.UsuarioDTO;
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
    public ResponseEntity<UsuarioDTO> criar(
            @RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO usuarioCriado = usuarioService.criar(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        
        return ResponseEntity.ok(usuarios); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(
            @PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        
        return ResponseEntity.ok(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody UsuarioUpdateDTO dto) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizar(id, dto);
        
        return ResponseEntity.ok(usuarioAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){
        usuarioService.deletar(id);
        
        return ResponseEntity.noContent().build();
    }
}