package br.com.barberscheduler.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    /**
     * Encontra agendamentos de um profissional que se sobrepõem a um novo
     * intervalo de tempo.
     * Esta consulta detecta colisão, ou seja, ela procura por agendamentos
     * com status CONFIRMADO ou PENDENTE e que satisfaçam a seguinte lógica:
     * (Inicio do Agendamento Antigo < Fim do Novo) e (Fim do Agendamento
     * Antigo > Início do Novo)
    */
    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId " +
            "AND (a.status = 'CONFIRMADO' OR a.status = 'PENDENTE') " +
            "AND a.dataHoraInicio < :fim " +
            "AND a.dataHoraFim > :inicio")
    List<Agendamento> findOverlappingAppointments(
            @Param("profissionalId") Long profissionalId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
    
    
    /**
     * Encontra todos os agendamentos (ativos) de um cliente específico.
    */
    List<Agendamento> findByClienteId(Long clienteId);
    
    /**
     * Encontra todos os agendamentos (ativos) de um profissional em um
     * intervalos de datas.
    */
    List<Agendamento> findByProfissionalIdAndDataHoraInicioBetween(
            Long profissionalId,
            LocalDateTime inicio,
            LocalDateTime fim);
}
