package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteContatoAnjoTest {

    @Mock
    private ContatoAnjoGateway contatoAnjoGateway;

    @InjectMocks
    private DeleteContatoAnjo deleteContatoAnjo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ContatoAnjo gerarContatoAnjoFake() {
        return ContatoAnjo.builder()
                .idContatoAnjo(1)
                .nomeContatoAnjo("Maria Silva")
                .build();
    }

    @Test
    void deveDeletarContatoAnjoQuandoExistir() {
        ContatoAnjo contato = gerarContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoById(contato.getIdContatoAnjo()))
                .thenReturn(Optional.of(contato));

        // método void, só verificamos se foi chamado
        deleteContatoAnjo.execute(contato.getIdContatoAnjo());

        verify(contatoAnjoGateway).getContatoAnjoById(contato.getIdContatoAnjo());
        verify(contatoAnjoGateway).deleteContatoAnjo(contato.getIdContatoAnjo());
    }

    @Test
    void deveLancarExcecaoQuandoContatoAnjoNaoEncontrado() {
        Integer idInexistente = 99;

        when(contatoAnjoGateway.getContatoAnjoById(idInexistente))
                .thenReturn(Optional.empty());

        ContatoAnjoNaoEncontradoException ex = assertThrows(ContatoAnjoNaoEncontradoException.class, () -> {
            deleteContatoAnjo.execute(idInexistente);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(idInexistente.toString()));

        verify(contatoAnjoGateway).getContatoAnjoById(idInexistente);
        verify(contatoAnjoGateway, never()).deleteContatoAnjo(anyInt());
    }
}
