package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.dto.UpdateContatoAnjoRequest;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateContatoAnjoTest {

    @Mock
    private ContatoAnjoGateway contatoAnjoGateway;

    @InjectMocks
    private UpdateContatoAnjo updateContatoAnjo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ContatoAnjo gerarContatoAnjoFake() {
        return ContatoAnjo.builder()
                .idContatoAnjo(1)
                .nomeContatoAnjo("Maria Silva")
                .emailContatoAnjo("maria@teste.com")
                .telefoneContatoAnjo("11999998888")
                .observacaoContatoAnjo("Nenhuma")
                .pacienteList(Collections.emptyList())
                .build();
    }

    private UpdateContatoAnjoRequest gerarRequestAtualizacao() {
        UpdateContatoAnjoRequest request = new UpdateContatoAnjoRequest();
        request.setNomeContatoAnjo("Maria Atualizada");
        request.setEmailContatoAnjo("maria.atualizada@teste.com");
        request.setTelefoneContatoAnjo("11988887777");
        request.setObservacaoContatoAnjo("Atualizado");
        request.setPacienteList(Collections.emptyList());
        return request;
    }

    @Test
    void deveAtualizarContatoAnjoComSucesso() {
        ContatoAnjo contatoOriginal = gerarContatoAnjoFake();
        UpdateContatoAnjoRequest updateRequest = gerarRequestAtualizacao();

        when(contatoAnjoGateway.getContatoAnjoById(contatoOriginal.getIdContatoAnjo()))
                .thenReturn(Optional.of(contatoOriginal));

        when(contatoAnjoGateway.updateContatoAnjo(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ContatoAnjo atualizado = updateContatoAnjo.execute(contatoOriginal.getIdContatoAnjo(), updateRequest);

        assertNotNull(atualizado);
        assertEquals("Maria Atualizada", atualizado.getNomeContatoAnjo());
        assertEquals("maria.atualizada@teste.com", atualizado.getEmailContatoAnjo());
        assertEquals("11988887777", atualizado.getTelefoneContatoAnjo());
        assertEquals("Atualizado", atualizado.getObservacaoContatoAnjo());
        assertEquals(Collections.emptyList(), atualizado.getPacienteList());

        verify(contatoAnjoGateway).updateContatoAnjo(any(ContatoAnjo.class));
    }

    @Test
    void deveLancarExcecaoQuandoContatoAnjoNaoEncontrado() {
        Integer idInexistente = 99;
        UpdateContatoAnjoRequest updateRequest = gerarRequestAtualizacao();

        when(contatoAnjoGateway.getContatoAnjoById(idInexistente))
                .thenReturn(Optional.empty());

        ContatoAnjoNaoEncontradoException ex = assertThrows(ContatoAnjoNaoEncontradoException.class, () -> {
            updateContatoAnjo.execute(idInexistente, updateRequest);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(idInexistente.toString()));

        verify(contatoAnjoGateway, never()).updateContatoAnjo(any());
    }
}
