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
    public List<UsuarioDTO> listarTodos() {
        enableFilter(filtro);
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        enableFilter(filtro);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário de ID " + id + " não encontrado ou inativo."));
        
        return new UsuarioDTO(usuario);
    }
    
    @Transactional
    public UsuarioDTO criar(UsuarioCreateDTO dto) {   
        disableFilter(filtro);
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
        
        return new UsuarioDTO(usuarioSalvo);
    }
    
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        Usuario usuarioExistente = findEntidadeById(id);
        
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
        
        if(dto.getPerfil() != null) {
            usuarioExistente.setPerfil(dto.getPerfil());
        }
        
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        
        return new UsuarioDTO(usuarioAtualizado);
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
