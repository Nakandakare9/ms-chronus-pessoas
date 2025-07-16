package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProfissionalSaudeTest {

    @Mock
    private ProfissionalSaudeGateway profissionalSaudeGateway;

    @InjectMocks
    private CreateProfissionalSaude createProfissionalSaude;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ProfissionalSaude gerarProfissionalFake() {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("Dra. Ana Paula")
                .emailProfissionalSaude("ana.paula@teste.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .crmProfissionalSaude("CRM-123456")
                .pacienteList(Collections.emptyList())
                .build();
    }

    @Test
    void deveCriarProfissionalSaudeQuandoNaoExistente() {
        ProfissionalSaude request = gerarProfissionalFake();

        when(profissionalSaudeGateway.getProfissionalSaudeById(request.getIdProfissionalSaude()))
                .thenReturn(Optional.empty());

        when(profissionalSaudeGateway.createProfissionalSaude(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProfissionalSaude resultado = createProfissionalSaude.execute(request);

        assertNotNull(resultado);
        assertEquals(request.getNomeProfissionalSaude(), resultado.getNomeProfissionalSaude());
        assertEquals(request.getEmailProfissionalSaude(), resultado.getEmailProfissionalSaude());
        assertEquals(request.getCrmProfissionalSaude(), resultado.getCrmProfissionalSaude());

        verify(profissionalSaudeGateway).getProfissionalSaudeById(request.getIdProfissionalSaude());
        verify(profissionalSaudeGateway).createProfissionalSaude(any(ProfissionalSaude.class));
    }

    @Test
    void deveLancarExcecaoQuandoProfissionalJaExistente() {
        ProfissionalSaude request = gerarProfissionalFake();

        when(profissionalSaudeGateway.getProfissionalSaudeById(request.getIdProfissionalSaude()))
                .thenReturn(Optional.of(request));

        ProfissionalSaudeExistenteException ex = assertThrows(ProfissionalSaudeExistenteException.class, () -> {
            createProfissionalSaude.execute(request);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains(request.getNomeProfissionalSaude()));
        verify(profissionalSaudeGateway, never()).createProfissionalSaude(any());
    }
}

