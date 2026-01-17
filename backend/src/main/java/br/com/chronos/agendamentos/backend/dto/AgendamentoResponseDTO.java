package br.com.chronos.agendamentos.backend.dto;

import java.time.LocalDateTime;

import br.com.chronos.agendamentos.backend.model.Agendamento;
import br.com.chronos.agendamentos.backend.model.enums.StatusAgendamento;

public record AgendamentoResponseDTO(
    Long id,
    UsuarioResponseDTO cliente,
    ProfissionalResponseDTO profissional,
    ServicoResponseDTO servico,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    StatusAgendamento status,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
            agendamento.getId(),
            new UsuarioResponseDTO(agendamento.getCliente()),
            new ProfissionalResponseDTO(agendamento.getProfissional()),
            new ServicoResponseDTO(agendamento.getServico()),
            agendamento.getDataHoraInicio(),
            agendamento.getDataHoraFim(),
            agendamento.getStatus(),
            agendamento.getCriadoEm(),
            agendamento.getAtualizadoEm()
        );
    }
}