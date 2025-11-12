package br.com.barberscheduler.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.barberscheduler.backend.model.Usuario;

public interface AutenticacaoRepository extends JpaRepository<Usuario, Long>{
    UserDetails findByEmail(String email);
}
