package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.ServicoDTO;
import br.com.barberscheduler.backend.dto.ServicoRequestDTO;
import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.repository.ServicoRepository;
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
    public List<ServicoDTO> listarTodos() {
        enableFilter(filtro);
        return servicoRepository.findAll()
                .stream()
                .map(ServicoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ServicoDTO buscarPorId(Long id) {
        enableFilter(filtro);
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado ou inativo."));
        
        return new ServicoDTO(servico);
    }
    
    @Transactional
    public ServicoDTO criar(ServicoRequestDTO dto) { 
        disableFilter(filtro);
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
        Servico servicoExistente = findEntidadeById(id);
           
        if(dto.getNome() != null && 
                !dto.getNome().equals(servicoExistente.getNome())) {
            servicoRepository.findByNomeRegardlessOfStatus(dto.getNome())
                .ifPresent(_ -> {
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
        enableFilter(filtro);
        if(!servicoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Serviço de ID " + id + " não encontrado ou inativo.");
        }
        servicoRepository.deleteById(id);
    }
}