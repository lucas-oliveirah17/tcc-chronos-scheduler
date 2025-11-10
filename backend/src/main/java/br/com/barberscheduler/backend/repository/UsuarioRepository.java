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
    
    /**
     * Encontra um usuário (ativo) pelo email.
    */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Lista todos os usuários (ativo) de um determinado perfil.
    */
    List<Usuario> findAllByPerfil(PerfilUsuario perfil);
    
    /**
     * Encontra um usuário (ativo ou não) pelo email.
    */
    @Query(value = "SELECT * FROM usuarios WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<Usuario> findByEmailRegardlessOfStatus(@Param("email") String email);
    
    /**
     * Verifica se um usuário existe ou não pelo email (ativo ou não).
    */
    @Query(value = "SELECT COUNT(1) > 0 FROM usuarios WHERE email = :email", nativeQuery = true)
    boolean existsByEmailRegardlessOfStatus(@Param("email") String email);
}
