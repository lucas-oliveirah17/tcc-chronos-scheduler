package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import br.com.barberscheduler.backend.model.Agendamento;
import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
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
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAllWithDetails();
    }
    
    @Transactional(readOnly = true)
    public Agendamento buscarPorId(Long id) {
        Optional<Agendamento> agendamento = agendamentoRepository.findByIdWithDetails(id);
        return agendamento.orElseThrow(
                () -> new EntityNotFoundException(
                        "Agendamento de ID " + id + " não encontrado."));
    }
    
    @Transactional
    public Agendamento criar(Agendamento agendamento) {
        Usuario cliente = usuarioService.buscarPorId(agendamento.getCliente().getId());
        Profissional profissional = profissionalService.buscarPorId(agendamento.getProfissional().getId());
        Servico servico = servicoService.buscarPorId(agendamento.getServico().getId());
        
        if(cliente == null ||
                cliente.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do cliente é obrigatório para criar um agendamento.");
        }
        
        if(profissional == null ||
                profissional.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do profissional é obrigatório para criar um agendamento.");
        }
        
        if(servico == null ||
                servico.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do servico é obrigatório para criar um agendamento.");
        }
                
        if(cliente.getPerfil() != PerfilUsuario.CLIENTE) {
            throw new IllegalArgumentException(
                    "O usuário de ID " + cliente.getId() + " não tem perfil de cliente.");
        }
        
        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        
        return agendamentoRepository.save(agendamento);
    }
    
    @Transactional
    public Agendamento atualizar(Long id, Agendamento agendamentoAtualizado) {
        Agendamento agendamentoExistente = agendamentoRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agendamento de ID " + id + " não encontrado."));
        
        agendamentoExistente.setDataHoraInicio(agendamentoAtualizado.getDataHoraInicio());
        agendamentoExistente.setDataHoraFim(agendamentoAtualizado.getDataHoraFim());
        agendamentoExistente.setStatus(agendamentoAtualizado.getStatus());
        agendamentoExistente.setCliente(agendamentoAtualizado.getCliente());
        agendamentoExistente.setProfissional(agendamentoAtualizado.getProfissional());
        agendamentoExistente.setServico(agendamentoAtualizado.getServico());
        
        return agendamentoRepository.save(agendamentoExistente);
    }
    
    @Transactional
    public void deletar(Long id) {
        if(!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Agendamento de ID " + id + " não encontrado.");
        }
        agendamentoRepository.deleteById(id);
    }
}
