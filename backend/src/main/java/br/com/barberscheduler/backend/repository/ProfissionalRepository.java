package br.com.barberscheduler.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long>{
    
    Optional<Profissional> findByUsuarioId (Long usuarioId);
    
    @Query(value = "SELECT COUNT(1) > 0 FROM profissionais WHERE usuario_id = :usuarioId", nativeQuery = true)
    boolean existsByUsuarioIdRegardlessOfStatus(@Param("usuarioId") Long usuarioId);
}