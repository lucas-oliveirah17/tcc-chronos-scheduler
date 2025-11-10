package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.ServicoDTO;
import br.com.barberscheduler.backend.dto.ServicoRequestDTO;
import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.repository.ServicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;
    
    public ServicoService(
            ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    @Transactional(readOnly = true)
    public Servico findEntidadeById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo.") );
    }
    
    @Transactional(readOnly = true)
    public List<ServicoDTO> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(ServicoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ServicoDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo."));
        
        return new ServicoDTO(servico);
    }
    
    @Transactional
    public ServicoDTO criar(ServicoRequestDTO dto) { 
        if(servicoRepository.existsByNomeRegardlessOfStatus(dto.getNome())) {
            throw new IllegalArgumentException(
                    "O serviço " + dto.getNome() + " já está cadastrado.");
        }
        
        Servico novoServico = new Servico();
        novoServico.setNome(dto.getNome());
        novoServico.setDescricao(dto.getDescricao());
        novoServico.setDuracaoMinutos(dto.getDuracaoMinutos());
        novoServico.setPreco(dto.getPreco());
        
        Servico servicoSalvo = servicoRepository.save(novoServico);
        
        return new ServicoDTO(servicoSalvo);
    }
    
    @Transactional
    public ServicoDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo."));
           
        if(dto.getNome() != null && 
                !dto.getNome().equals(servicoExistente.getNome())) {
            servicoRepository.findByNomeRegardlessOfStatus(dto.getNome())
                .ifPresent(u -> {
                    throw new IllegalArgumentException(
                            "O nome de serviço " + dto.getNome() + " já está cadastrado.");
                    });
            servicoExistente.setNome(dto.getNome());
        }
        
        if(dto.getDescricao() != null) {
            servicoExistente.setDescricao(dto.getDescricao());
        }
        
        if(dto.getDuracaoMinutos() != null) {
            servicoExistente.setDuracaoMinutos(dto.getDuracaoMinutos());
        }
        
        if(dto.getPreco() != null) {
            servicoExistente.setPreco(dto.getPreco());
        }
        
        Servico servicoAtualizado = servicoRepository.save(servicoExistente);
        
        return new ServicoDTO(servicoAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
        if(!servicoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Serviço de ID " + id + " não encontrado ou inativo.");
        }
        servicoRepository.deleteById(id);
    }
}