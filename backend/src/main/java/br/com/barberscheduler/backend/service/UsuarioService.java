package br.com.barberscheduler.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.UsuarioRequestDTO;
import br.com.barberscheduler.backend.dto.UsuarioResponseDTO;
import br.com.barberscheduler.backend.dto.UsuarioUpdateRequestDTO;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService extends BaseService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String filtro = "usuarioAtivo";
    
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            EntityManager entityManager) {
        super(entityManager);
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
        
    @Transactional(readOnly = true)
    public Usuario findEntidadeById(Long id) {
        enableFilter(filtro);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo.") );
    }
    
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        enableFilter(filtro);
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        enableFilter(filtro);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo."));
        
        return new UsuarioResponseDTO(usuario);
    }
    
    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        disableFilter(filtro);
        if(usuarioRepository.existsByEmailRegardlessOfStatus(dto.email())) {
            throw new IllegalArgumentException(
                    "O e-mail " + dto.email() + " já está cadastrado.");
        }
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setTelefone(dto.telefone());
        novoUsuario.setPerfil(dto.perfil());
        
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        novoUsuario.setSenha(senhaCriptografada);
        
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        
        return new UsuarioResponseDTO(usuarioSalvo);
    }
    
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateRequestDTO dto) {
        Usuario usuarioExistente = findEntidadeById(id);
        
        if(dto.email() != null && 
                !dto.email().equals(usuarioExistente.getEmail())) {
            
            usuarioRepository.findByEmailRegardlessOfStatus(dto.email())
                .ifPresent(_ -> {
                    throw new IllegalArgumentException(
                            "O e-mail " + dto.email() + " já está cadastrado.");
                    });
            
            usuarioExistente.setEmail(dto.email());
        }
        
        if(dto.nome() != null) {
            usuarioExistente.setNome(dto.nome());
        }
        
        if(dto.telefone() != null) {
            usuarioExistente.setTelefone(dto.telefone());
        }
        
        if(dto.perfil() != null) {
            usuarioExistente.setPerfil(dto.perfil());
        }
        
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        
        return new UsuarioResponseDTO(usuarioAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
        enableFilter(filtro);
        if(!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Usuário de ID " + id + " não encontrado ou inativo.");
        }
        
        usuarioRepository.deleteById(id);
    }
}
