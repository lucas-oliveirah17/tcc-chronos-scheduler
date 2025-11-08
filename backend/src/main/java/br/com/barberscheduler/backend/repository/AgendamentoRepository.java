package br.com.barberscheduler.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    @Query("SELECT a FROM Agendamento a " +
           "JOIN FETCH a.cliente " +
           "JOIN FETCH a.profissional " +
           "JOIN FETCH a.servico")
    List<Agendamento> findAllWithDetails();
    
    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.cliente " +
            "JOIN FETCH a.profissional " +
            "JOIN FETCH a.servico " +
            "WHERE a.id = :id")
    Optional<Agendamento> findByIdWithDetails(@Param("id") Long id);
}
