package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.barberscheduler.backend.dto.AgendamentoDTO;
import br.com.barberscheduler.backend.dto.AgendamentoRequestDTO;
import br.com.barberscheduler.backend.model.Agendamento;
import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
import br.com.barberscheduler.backend.model.enums.StatusAgendamento;
import br.com.barberscheduler.backend.repository.AgendamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioService usuarioService;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;
    
    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            UsuarioService usuarioService,
            ProfissionalService profissionalService,
            ServicoService servicoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioService = usuarioService;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
    }
        
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(AgendamentoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agendamento de ID " + id + " não encontrado ou inativo."));
        
        return new AgendamentoDTO(agendamento);
    }
    
    @Transactional
    public AgendamentoDTO criar(AgendamentoRequestDTO dto) {
        Usuario cliente = usuarioService.findEntidadeById(dto.getClienteId());
        Profissional profissional = profissionalService.findEntidadeById(dto.getProfissionalId());
        Servico servico = servicoService.findEntidadeById(dto.getServicoId());
        
        if(cliente.getPerfil() != PerfilUsuario.CLIENTE) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + cliente.getId() + " não tem perfil de Cliente.");
        }
        
        LocalDateTime inicio = dto.getDataHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());
        
        List<Agendamento> conflitos = agendamentoRepository.findOverlappingAppointments(
                profissional.getId(), inicio, fim);
        
        if(!conflitos.isEmpty()) {
            throw new IllegalArgumentException(
                    "O Profissional já possui um agendamento conflitanto neste horário.");
        }
        
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setCliente(cliente);
        novoAgendamento.setProfissional(profissional);
        novoAgendamento.setServico(servico);
        novoAgendamento.setDataHoraInicio(inicio);
        novoAgendamento.setDataHoraFim(fim);
        novoAgendamento.setStatus(StatusAgendamento.CONFIRMADO);
        
        Agendamento agendamentoSalvo = agendamentoRepository.save(novoAgendamento);
        
        return new AgendamentoDTO(agendamentoSalvo);
    }
    
    @Transactional
    public void deletar(Long id) {
        if(!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Agendamento de ID " + id + " não encontrado ou inativo.");
        }
        agendamentoRepository.deleteById(id);
    }
}
