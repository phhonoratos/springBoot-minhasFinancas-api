package com.honorato.minhasfinancas.model.repositories;

import java.util.Optional;

import com.honorato.minhasfinancas.model.entities.Usuario;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void verifyIfExistEmail() {
        //cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void falseIfNotExistEmailRegistered() {
        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void persistUserInDataBase() {
        //cenário
        Usuario usuario = criarUsuario();
        //ação
        Usuario usuarioSalvo = repository.save(usuario);

        //varificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void findUserByEmail() {
        //cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @Test
    public void nullIfNotExistUserByEmailWhenIsntExistInTheBase() {
        //verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();

    }

    public static Usuario criarUsuario() {
        return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
    }
    
}
