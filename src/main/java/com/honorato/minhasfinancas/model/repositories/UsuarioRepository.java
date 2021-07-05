package com.honorato.minhasfinancas.model.repositories;

import java.util.Optional;

import com.honorato.minhasfinancas.model.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
    
}
