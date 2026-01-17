package br.com.chronos.agendamentos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.chronos.agendamentos.backend.model.Usuario;

public interface AutenticacaoRepository extends JpaRepository<Usuario, Long>{
    UserDetails findByEmail(String email);
}
