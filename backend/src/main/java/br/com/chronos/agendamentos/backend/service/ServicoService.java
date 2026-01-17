package br.com.chronos.agendamentos.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.agendamentos.backend.dto.ServicoRequestDTO;
import br.com.chronos.agendamentos.backend.dto.ServicoResponseDTO;
import br.com.chronos.agendamentos.backend.model.Servico;
import br.com.chronos.agendamentos.backend.repository.ServicoRepository;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicoService extends BaseService {
    private final ServicoRepository servicoRepository;
    private final String filtro = "servicoAtivo";

    
    public ServicoService(
            ServicoRepository servicoRepository,
            EntityManager entityManager) {
        super(entityManager);
        this.servicoRepository = servicoRepository;
    }
    
    @Transactional(readOnly = true)
    public Servico findEntidadeById(Long id) {
        enableFilter(filtro);
        return servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo.") );
    }
    
    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodos() {
        enableFilter(filtro);
        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        enableFilter(filtro);
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo."));
        
        return new ServicoResponseDTO(servico);
    }
    
    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO dto) { 
        disableFilter(filtro);
        if(servicoRepository.existsByNomeRegardlessOfStatus(dto.nome())) {
            throw new IllegalArgumentException(
                    "O serviço " + dto.nome() + " já está cadastrado.");
        }
        
        Servico novoServico = new Servico();
        novoServico.setNome(dto.nome());
        novoServico.setDescricao(dto.descricao());
        novoServico.setDuracaoMinutos(dto.duracaoMinutos());
        novoServico.setPreco(dto.preco());
        
        Servico servicoSalvo = servicoRepository.save(novoServico);
        
        return new ServicoResponseDTO(servicoSalvo);
    }
    
    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servicoExistente = findEntidadeById(id);
           
        if(dto.nome() != null && 
                !dto.nome().equals(servicoExistente.getNome())) {
            servicoRepository.findByNomeRegardlessOfStatus(dto.nome())
                .ifPresent(_ -> {
                    throw new IllegalArgumentException(
                            "O nome de serviço " + dto.nome() + " já está cadastrado.");
                    });
            servicoExistente.setNome(dto.nome());
        }
        
        if(dto.descricao() != null) {
            servicoExistente.setDescricao(dto.descricao());
        }
        
        if(dto.duracaoMinutos() != null) {
            servicoExistente.setDuracaoMinutos(dto.duracaoMinutos());
        }
        
        if(dto.preco() != null) {
            servicoExistente.setPreco(dto.preco());
        }
        
        Servico servicoAtualizado = servicoRepository.save(servicoExistente);
        
        return new ServicoResponseDTO(servicoAtualizado);
    }
    
    @Transactional
    public void deletar(Long id) {
        enableFilter(filtro);
        if(!servicoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Serviço de ID " + id + " não encontrado ou inativo.");
        }
        servicoRepository.deleteById(id);
    }
}