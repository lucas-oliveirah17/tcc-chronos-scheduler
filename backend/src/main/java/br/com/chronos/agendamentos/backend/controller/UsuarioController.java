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

import br.com.chronos.agendamentos.backend.config.SecurityConfig;
import br.com.chronos.agendamentos.backend.dto.UsuarioRequestDTO;
import br.com.chronos.agendamentos.backend.dto.UsuarioResponseDTO;
import br.com.chronos.agendamentos.backend.dto.UsuarioUpdateRequestDTO;
import br.com.chronos.agendamentos.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "usuarios")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    @Operation(summary = "Cria um Usuário", description = "Método para criar um Usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
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
            @RequestBody UsuarioUpdateRequestDTO dto) {
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