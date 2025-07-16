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
import static org.mockito.Mockito.*;

class DeleteProfissionalSaudeTest {

    @Mock
    private ProfissionalSaudeGateway profissionalSaudeGateway;

    @InjectMocks
    private DeleteProfissionalSaude deleteProfissionalSaude;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ProfissionalSaude gerarProfissionalFake() {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("Dra. Ana")
                .emailProfissionalSaude("ana@teste.com")
                .crmProfissionalSaude("CRM123")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .pacienteList(Collections.emptyList())
                .build();
    }

    @Test
    void deveDeletarProfissionalSaudeQuandoExistir() {
        ProfissionalSaude profissional = gerarProfissionalFake();

        when(profissionalSaudeGateway.getProfissionalSaudeById(profissional.getIdProfissionalSaude()))
                .thenReturn(Optional.of(profissional));

        deleteProfissionalSaude.execute(profissional.getIdProfissionalSaude());

        verify(profissionalSaudeGateway).getProfissionalSaudeById(profissional.getIdProfissionalSaude());
        verify(profissionalSaudeGateway).deleteProfissionalSaude(profissional.getIdProfissionalSaude());
    }

    @Test
    void deveLancarExcecaoQuandoProfissionalSaudeNaoEncontrado() {
        Integer idInexistente = 99;

        when(profissionalSaudeGateway.getProfissionalSaudeById(idInexistente))
                .thenReturn(Optional.empty());

        ProfissionalSaudeNaoEncontradoException ex = assertThrows(ProfissionalSaudeNaoEncontradoException.class, () -> {
            deleteProfissionalSaude.execute(idInexistente);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(idInexistente.toString()));

        verify(profissionalSaudeGateway).getProfissionalSaudeById(idInexistente);
        verify(profissionalSaudeGateway, never()).deleteProfissionalSaude(anyInt());
    }
}

