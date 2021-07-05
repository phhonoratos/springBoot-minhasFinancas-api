package com.honorato.minhasfinancas.services.impl;

import java.util.Optional;

import com.honorato.minhasfinancas.exceptions.ErroAutenticacao;
import com.honorato.minhasfinancas.exceptions.RegraNegocioException;
import com.honorato.minhasfinancas.model.entities.Usuario;
import com.honorato.minhasfinancas.model.repositories.UsuarioRepository;
import com.honorato.minhasfinancas.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }
        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacao("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
        
    }

    @Override
    public Optional<Usuario> buscaPorId(Long id) {
        return repository.findById(id);
    }
    
}
