package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetProfissionalSaudeTest {

    @Mock
    private ProfissionalSaudeGateway profissionalSaudeGateway;

    @InjectMocks
    private GetProfissionalSaude getProfissionalSaude;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ProfissionalSaude gerarProfissionalFake() {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("Dra. Carla Souza")
                .emailProfissionalSaude("carla@hospital.com")
                .crmProfissionalSaude("CRM987654")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .pacienteList(Collections.emptyList())
                .build();
    }

    @Test
    void deveRetornarProfissionalSaudeQuandoEncontrado() {
        ProfissionalSaude profissional = gerarProfissionalFake();

        when(profissionalSaudeGateway.getProfissionalSaudeById(profissional.getIdProfissionalSaude()))
                .thenReturn(Optional.of(profissional));

        ProfissionalSaude resultado = getProfissionalSaude.execute(profissional.getIdProfissionalSaude());

        assertNotNull(resultado);
        assertEquals(profissional.getIdProfissionalSaude(), resultado.getIdProfissionalSaude());
        assertEquals(profissional.getNomeProfissionalSaude(), resultado.getNomeProfissionalSaude());

        verify(profissionalSaudeGateway).getProfissionalSaudeById(profissional.getIdProfissionalSaude());
    }

    @Test
    void deveLancarExcecaoQuandoProfissionalNaoEncontrado() {
        Integer idInexistente = 404;

        when(profissionalSaudeGateway.getProfissionalSaudeById(idInexistente))
                .thenReturn(Optional.empty());

        ProfissionalSaudeNaoEncontradoException ex = assertThrows(ProfissionalSaudeNaoEncontradoException.class, () -> {
            getProfissionalSaude.execute(idInexistente);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(idInexistente.toString()));

        verify(profissionalSaudeGateway).getProfissionalSaudeById(idInexistente);
    }
}

