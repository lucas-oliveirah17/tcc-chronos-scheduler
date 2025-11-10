package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.ProfissionalDTO;
import br.com.barberscheduler.backend.dto.ProfissionalRequestDTO;
import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
import br.com.barberscheduler.backend.repository.ProfissionalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioService usuarioService;
    
    public ProfissionalService(
            ProfissionalRepository profissionalRepository, 
            UsuarioService usuarioService) {
        this.profissionalRepository = profissionalRepository;
        this.usuarioService = usuarioService;
    }
    
    @Transactional(readOnly = true)
    public Profissional findEntidadeById(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo.") );
    }
    
    // Transactional garante que as operações do banco de dados sejam atômicas, 
    // ou seja, se uma das consultas falharem, ele faz o rollback de todas as alterações
    // que fez nesta tentativa.
    @Transactional(readOnly = true)
    public List<ProfissionalDTO> listarTodos() {      
        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProfissionalDTO buscarPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo."));
        
        return new ProfissionalDTO(profissional);
    }
    
    @Transactional
    public ProfissionalDTO criar(ProfissionalRequestDTO dto) {
        if(dto.getUsuarioId() == null) {
            throw new IllegalArgumentException(
                    "O ID do usuário é obrigatório para criar um profissional.");
        }
        
        Usuario usuarioAssociado = usuarioService.findEntidadeById(dto.getUsuarioId());
        
        if(usuarioAssociado.getPerfil() != PerfilUsuario.PROFISSIONAL) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + dto.getUsuarioId() + " não tem perfil de Profissional.");
        }
        
        if(profissionalRepository.existsByUsuarioIdRegardlessOfStatus(dto.getUsuarioId())) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + dto.getUsuarioId() + " já está associado a outro profissional.");
        }
        
        Profissional novoProfissional = new Profissional();
        novoProfissional.setEspecialidades(dto.getEspecialidades());
        novoProfissional.setUsuario(usuarioAssociado);
        
        Profissional profissionalSalvo = profissionalRepository.save(novoProfissional);
        
        return new ProfissionalDTO(profissionalSalvo);
    }
    
    @Transactional
    public ProfissionalDTO atualizar(Long id, ProfissionalRequestDTO dto) {
        Profissional profissionalExistente = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado ou inativo."));
        
        if(dto.getEspecialidades() != null) {
            profissionalExistente.setEspecialidades(dto.getEspecialidades());
        }
        
        if(dto.getUsuarioId() != null &&
                !dto.getUsuarioId().equals(profissionalExistente.getUsuario().getId())) {
            Usuario novoUsuario = usuarioService.findEntidadeById(dto.getUsuarioId());
            
            if(novoUsuario.getPerfil() != PerfilUsuario.PROFISSIONAL) {
                throw new IllegalArgumentException(
                        "O novo usuário de ID " + dto.getUsuarioId() + " não tem perfil de Profissional.");
            }
            
            if(profissionalRepository.existsByUsuarioIdRegardlessOfStatus(dto.getUsuarioId())) {
                throw new IllegalArgumentException(
                        "O novo usuário de ID " + dto.getUsuarioId() + " já está associado a outro profissional."); 
            }
            
            profissionalExistente.setUsuario(novoUsuario);
        }
            
        
        Profissional profissionalAtualizado = profissionalRepository.save(profissionalExistente);
        
        return new ProfissionalDTO(profissionalAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
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