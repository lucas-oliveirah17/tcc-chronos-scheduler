package br.com.barberscheduler.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findAllByPerfil(PerfilUsuario perfil);
    
    @Query(value = "SELECT * FROM usuarios WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<Usuario> findByEmailRegardlessOfStatus(@Param("email") String email);
    
    @Query(value = "SELECT COUNT(1) > 0 FROM usuarios WHERE email = :email", nativeQuery = true)
    boolean existsByEmailRegardlessOfStatus(@Param("email") String email);
}
