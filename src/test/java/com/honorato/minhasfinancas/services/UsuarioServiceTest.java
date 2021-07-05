package com.honorato.minhasfinancas.services;

import java.util.Optional;

import com.honorato.minhasfinancas.exceptions.ErroAutenticacao;
import com.honorato.minhasfinancas.exceptions.RegraNegocioException;
import com.honorato.minhasfinancas.model.entities.Usuario;
import com.honorato.minhasfinancas.model.repositories.UsuarioRepository;
import com.honorato.minhasfinancas.services.impl.UsuarioServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    
    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test(expected = Test.None.class)
    public void cadastrarUsuario() {
        //cenário
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                    .id(1l)
                    .nome("nome")
                    .email("email@email.com")
                    .senha("senha").build();
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //ação
        Usuario usuarioSalvo = service.cadastrarUsuario(new Usuario());

        //verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test(expected = RegraNegocioException.class)
    public void erroEmailJaCadastrado() {
        //cenário
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        //ação
        service.cadastrarUsuario(usuario);

        //verificação
        Mockito.verify(repository, Mockito.never()).save(usuario);

    }

    @Test(expected = Test.None.class)
    public void autenticarUsuarioComSucesso() {
        //cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //ação
        Usuario result = service.autenticar(email, senha);

        //verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void erroDeEmailNaAutenticacao() {
        //cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //ação
        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado.");
    }

    @Test
    public void erroDeSenhaNaAutenticacao() {
        //cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //ação
        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
    }

    @Test(expected = Test.None.class)
    public void validateEmail() {
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //ação
        service.validarEmail("usuario@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void throwExceptionWhenValidateEmailIfExistEmailRegistered() {
        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //ação
        service.validarEmail("usuari@email.com");
    }
}
