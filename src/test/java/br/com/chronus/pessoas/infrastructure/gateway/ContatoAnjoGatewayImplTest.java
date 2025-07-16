package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.enums.EnumParentesco;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ContatoAnjoEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.ContatoAnjoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContatoAnjoGatewayImplTest {

    @Mock
    private ContatoAnjoRepository contatoAnjoRepository;

    @InjectMocks
    private ContatoAnjoGatewayImpl contatoAnjoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ContatoAnjo criarContatoAnjoFake() {
        return ContatoAnjo.builder()
                .idContatoAnjo(1)
                .nomeContatoAnjo("Maria Anjo")
                .emailContatoAnjo("anjo@email.com")
                .cpfContatoAnjo("12345678909")
                .telefoneContatoAnjo("11999999999")
                .parentescoContatoAnjo(EnumParentesco.IRMA)
                .observacaoContatoAnjo("Contato de emergência")
                .pacienteList(List.of())
                .build();
    }

    @Test
    void deveCriarContatoAnjo() {
        ContatoAnjo contato = criarContatoAnjoFake();
        ContatoAnjoEntity entity = contatoAnjoGateway.mapToEntityContatoAnjo(contato);

        when(contatoAnjoRepository.save(any())).thenReturn(entity);

        ContatoAnjo result = contatoAnjoGateway.createContatoAnjo(contato);

        assertNotNull(result);
        assertEquals(contato.getNomeContatoAnjo(), result.getNomeContatoAnjo());
        verify(contatoAnjoRepository).save(any());
    }

    @Test
    void deveBuscarContatoAnjoPorId() {
        ContatoAnjo contato = criarContatoAnjoFake();
        ContatoAnjoEntity entity = contatoAnjoGateway.mapToEntityContatoAnjo(contato);

        when(contatoAnjoRepository.findById(1)).thenReturn(Optional.of(entity));

        Optional<ContatoAnjo> result = contatoAnjoGateway.getContatoAnjoById(1);

        assertTrue(result.isPresent());
        assertEquals("Maria Anjo", result.get().getNomeContatoAnjo());
    }

    @Test
    void deveAtualizarContatoAnjo() {
        ContatoAnjo contato = criarContatoAnjoFake();
        ContatoAnjoEntity entity = contatoAnjoGateway.mapToEntityContatoAnjo(contato);

        when(contatoAnjoRepository.findById(1)).thenReturn(Optional.of(entity));
        when(contatoAnjoRepository.save(any())).thenReturn(entity);

        ContatoAnjo result = contatoAnjoGateway.updateContatoAnjo(contato);

        assertNotNull(result);
        assertEquals("Maria Anjo", result.getNomeContatoAnjo());
    }

    @Test
    void deveLancarExcecaoAoAtualizarContatoInexistente() {
        ContatoAnjo contato = criarContatoAnjoFake();

        when(contatoAnjoRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                contatoAnjoGateway.updateContatoAnjo(contato));

        assertTrue(ex.getMessage().contains("ContatoAnjo não encontrado"));
    }

    @Test
    void deveDeletarContatoAnjo() {
        ContatoAnjo contato = criarContatoAnjoFake();
        ContatoAnjoEntity entity = contatoAnjoGateway.mapToEntityContatoAnjo(contato);

        when(contatoAnjoRepository.findById(1)).thenReturn(Optional.of(entity));
        doNothing().when(contatoAnjoRepository).delete(any());

        contatoAnjoGateway.deleteContatoAnjo(1);

        verify(contatoAnjoRepository).delete(entity);
    }

    @Test
    void deveLancarExcecaoAoDeletarContatoInexistente() {
        when(contatoAnjoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> contatoAnjoGateway.deleteContatoAnjo(1));
    }
}
