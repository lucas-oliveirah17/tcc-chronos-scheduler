package br.com.barberscheduler.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.UsuarioCreateDTO;
import br.com.barberscheduler.backend.dto.UsuarioDTO;
import br.com.barberscheduler.backend.dto.UsuarioUpdateDTO;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setPerfil(usuario.getPerfil());
        dto.setCriadoEm(usuario.getCriadoEm());
        
        return dto;
    }
    
    @Transactional(readOnly = true)
    public Usuario findEntidadeById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo.") );
    }
    
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo."));
        
        return converterParaDTO(usuario);
    }
    
    @Transactional
    public UsuarioDTO criar(UsuarioCreateDTO dto) {   
        if(usuarioRepository.existsByEmailRegardlessOfStatus(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "O e-mail " + dto.getEmail() + " já está cadastrado.");
        }
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setTelefone(dto.getTelefone());
        novoUsuario.setPerfil(dto.getPerfil());
        
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());       
        novoUsuario.setSenha(senhaCriptografada);
        
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        
        return converterParaDTO(usuarioSalvo);
    }
    
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo."));
        
        if(dto.getEmail() != null && 
                !dto.getEmail().equals(usuarioExistente.getEmail())) {
            usuarioRepository.findByEmailRegardlessOfStatus(dto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException(
                            "O e-mail " + dto.getEmail() + " já está cadastrado.");
                    });
            usuarioExistente.setEmail(dto.getEmail());
        }
        
        if(dto.getNome() != null) {
            usuarioExistente.setNome(dto.getNome());
        }
        
        if(dto.getTelefone() != null) {
            usuarioExistente.setTelefone(dto.getTelefone());
        }
        
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        
        return converterParaDTO(usuarioAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Usuário de ID " + id + " não encontrado ou inativo.");
        }
        
        usuarioRepository.deleteById(id);
    }
}
