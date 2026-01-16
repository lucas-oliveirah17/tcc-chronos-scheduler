package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.ProfissionalResponseDTO;
import br.com.barberscheduler.backend.dto.ProfissionalRequestDTO;
import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
import br.com.barberscheduler.backend.repository.ProfissionalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissionalService extends BaseService {
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioService usuarioService;
    private final String filtro = "profissionalAtivo";

    public ProfissionalService(
            ProfissionalRepository profissionalRepository, 
            UsuarioService usuarioService,
            EntityManager entityManager) {
        super(entityManager);
        this.profissionalRepository = profissionalRepository;
        this.usuarioService = usuarioService;
    }
    
    @Transactional(readOnly = true)
    public Profissional findEntidadeById(Long id) {
        enableFilter(filtro);
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo.") );
    }
    
    // Transactional garante que as operações do banco de dados sejam atômicas, 
    // ou seja, se uma das consultas falharem, ele faz o rollback de todas as alterações
    // que fez nesta tentativa.
    @Transactional(readOnly = true)
    public List<ProfissionalResponseDTO> listarTodos() {
        enableFilter(filtro);
        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProfissionalResponseDTO buscarPorId(Long id) {
        enableFilter(filtro);
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo."));
        
        return new ProfissionalResponseDTO(profissional);
    }
    
    @Transactional
    public ProfissionalResponseDTO criar(ProfissionalRequestDTO dto) {
        disableFilter(filtro);
        if(dto.usuarioId() == null) {
            throw new IllegalArgumentException(
                    "O ID do usuário é obrigatório para criar um profissional.");
        }
        
        Usuario usuarioAssociado = usuarioService.findEntidadeById(dto.usuarioId());
        
        if(usuarioAssociado.getPerfil() != PerfilUsuario.PROFISSIONAL) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + dto.usuarioId() + " não tem perfil de Profissional.");
        }
        
        if(profissionalRepository.existsByUsuarioIdRegardlessOfStatus(dto.usuarioId())) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + dto.usuarioId() + " já está associado a outro profissional.");
        }
        
        Profissional novoProfissional = new Profissional();
        novoProfissional.setEspecialidades(dto.especialidades());
        novoProfissional.setUsuario(usuarioAssociado);
        
        Profissional profissionalSalvo = profissionalRepository.save(novoProfissional);
        
        return new ProfissionalResponseDTO(profissionalSalvo);
    }
    
    @Transactional
    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO dto) {
        Profissional profissionalExistente = findEntidadeById(id);
        
        if(dto.especialidades() != null) {
            profissionalExistente.setEspecialidades(dto.especialidades());
        }
        
        if(dto.usuarioId() != null &&
                !dto.usuarioId().equals(profissionalExistente.getUsuario().getId())) {
            Usuario novoUsuario = usuarioService.findEntidadeById(dto.usuarioId());
            
            if(novoUsuario.getPerfil() != PerfilUsuario.PROFISSIONAL) {
                throw new IllegalArgumentException(
                        "O novo usuário de ID " + dto.usuarioId() + " não tem perfil de Profissional.");
            }
            
            if(profissionalRepository.existsByUsuarioIdRegardlessOfStatus(dto.usuarioId())) {
                throw new IllegalArgumentException(
                        "O novo usuário de ID " + dto.usuarioId() + " já está associado a outro profissional."); 
            }
            
            profissionalExistente.setUsuario(novoUsuario);
        }
            
        
        Profissional profissionalAtualizado = profissionalRepository.save(profissionalExistente);
        
        return new ProfissionalResponseDTO(profissionalAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
        enableFilter(filtro);
        Profissional profissionalParaDeletar = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo."));
        
        Usuario usuarioAssociado = profissionalParaDeletar.getUsuario();
        
        profissionalRepository.deleteById(id);
        
        if(usuarioAssociado != null) {
            usuarioService.deletar(usuarioAssociado.getId());
        }
    }
}