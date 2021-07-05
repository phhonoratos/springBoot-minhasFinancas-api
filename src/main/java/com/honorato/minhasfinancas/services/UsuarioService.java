package com.honorato.minhasfinancas.services;

import java.util.Optional;

import com.honorato.minhasfinancas.model.entities.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario cadastrarUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> buscaPorId(Long id);
    
}
