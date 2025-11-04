package br.com.barberscheduler.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario criarUsuario(Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        
        // AQUI POSTERIOMENTE ENTRA A CRIPTOGRAFIA DA SENHA POR SPRING SECURITY
        
        return usuarioRepository.save(usuario); 
    }
    
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario de id " + id + " não encontrado."));
        
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
        
        return usuarioRepository.save(usuarioExistente);
    }
    
    public void deletarUsuario(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario de id " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
