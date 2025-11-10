package br.com.barberscheduler.backend.dto;

import java.time.LocalDateTime;

import br.com.barberscheduler.backend.model.Agendamento;
import br.com.barberscheduler.backend.model.enums.StatusAgendamento;

// DTO para RETORNAR dados de Agendamento ao frontend
public class AgendamentoDTO {
    
    private Long id;
    private UsuarioDTO cliente;
    private ProfissionalDTO profissional;
    private ServicoDTO servico;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private StatusAgendamento status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public AgendamentoDTO() {
    }
    
    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.cliente = new UsuarioDTO(agendamento.getCliente());
        this.profissional = new ProfissionalDTO(agendamento.getProfissional());
        this.servico = new ServicoDTO(agendamento.getServico());
        this.dataHoraInicio = agendamento.getDataHoraInicio();
        this.dataHoraFim = agendamento.getDataHoraFim();
        this.status = agendamento.getStatus();
        this.criadoEm = agendamento.getCriadoEm();
        this.atualizadoEm = agendamento.getAtualizadoEm();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioDTO cliente) {
        this.cliente = cliente;
    }

    public ProfissionalDTO getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalDTO profissional) {
        this.profissional = profissional;
    }

    public ServicoDTO getServico() {
        return servico;
    }

    public void setServico(ServicoDTO servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
