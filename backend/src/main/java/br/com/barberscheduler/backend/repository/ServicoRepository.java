package br.com.barberscheduler.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
    Optional<Servico> findByNome(String nome);
    
    @Query(value = "SELECT * FROM servicos WHERE nome = :nome LIMIT 1", nativeQuery = true)
    Optional<Servico> findByNomeRegardlessOfStatus(@Param("nome") String nome);
    
    @Query(value = "SELECT COUNT(1) > 0 FROM servicos WHERE nome = :nome", nativeQuery = true)
    boolean existsByNomeRegardlessOfStatus(@Param("nome") String nome);
}