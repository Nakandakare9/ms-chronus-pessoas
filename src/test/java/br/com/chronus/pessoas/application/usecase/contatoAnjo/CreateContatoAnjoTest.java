package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.enums.EnumParentesco;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateContatoAnjoTest {

    @Mock
    private ContatoAnjoGateway contatoAnjoGateway;

    @InjectMocks
    private CreateContatoAnjo createContatoAnjo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ContatoAnjo gerarContatoAnjoFake() {
        return ContatoAnjo.builder()
                .idContatoAnjo(1)
                .nomeContatoAnjo("Maria Silva")
                .emailContatoAnjo("maria@teste.com")
                .cpfContatoAnjo("123.456.789-00")
                .telefoneContatoAnjo("11999998888")
                .parentescoContatoAnjo(EnumParentesco.MAE)
                .observacaoContatoAnjo("Nenhuma")
                .pacienteList(null)
                .build();
    }

    @Test
    void deveCriarContatoAnjoQuandoNaoExistir() {
        ContatoAnjo contatoRequest = gerarContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoById(contatoRequest.getIdContatoAnjo()))
                .thenReturn(Optional.empty());

        when(contatoAnjoGateway.createContatoAnjo(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ContatoAnjo resultado = createContatoAnjo.execute(contatoRequest);

        assertNotNull(resultado);
        assertEquals(contatoRequest.getNomeContatoAnjo(), resultado.getNomeContatoAnjo());

        verify(contatoAnjoGateway).getContatoAnjoById(contatoRequest.getIdContatoAnjo());
        verify(contatoAnjoGateway).createContatoAnjo(any());
    }

    @Test
    void deveLancarExcecaoQuandoContatoAnjoExistente() {
        ContatoAnjo contatoRequest = gerarContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoById(contatoRequest.getIdContatoAnjo()))
                .thenReturn(Optional.of(contatoRequest));

        ContatoAnjoExistenteException ex = assertThrows(ContatoAnjoExistenteException.class, () -> {
            createContatoAnjo.execute(contatoRequest);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(contatoRequest.getNomeContatoAnjo()));

        verify(contatoAnjoGateway).getContatoAnjoById(contatoRequest.getIdContatoAnjo());
        verify(contatoAnjoGateway, never()).createContatoAnjo(any());
    }
}

