package br.com.barberscheduler.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
        
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id, 
            @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();           
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id){
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
            
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();            
        }
    }
}
