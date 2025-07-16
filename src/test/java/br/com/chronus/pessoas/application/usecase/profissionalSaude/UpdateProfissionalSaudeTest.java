package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.dto.UpdateProfissionalSaudeRequest;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UpdateProfissionalSaudeTest {

    @Mock
    private ProfissionalSaudeGateway profissionalSaudeGateway;

    @InjectMocks
    private UpdateProfissionalSaude updateProfissionalSaude;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ProfissionalSaude gerarProfissionalOriginal() {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("Dr. José Antigo")
                .emailProfissionalSaude("jose@hospital.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .build();
    }

    private UpdateProfissionalSaudeRequest gerarRequestAtualizacao() {
        UpdateProfissionalSaudeRequest request = new UpdateProfissionalSaudeRequest();
        request.setNomeProfissionalSaude("Dr. José Atualizado");
        request.setEmailProfissionalSaude("jose.atualizado@hospital.com");
        request.setEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.PSICOLOGO);
        return request;
    }

    @Test
    void deveAtualizarProfissionalSaudeComSucesso() {
        ProfissionalSaude profissionalOriginal = gerarProfissionalOriginal();
        UpdateProfissionalSaudeRequest updateRequest = gerarRequestAtualizacao();

        when(profissionalSaudeGateway.getProfissionalSaudeById(1))
                .thenReturn(Optional.of(profissionalOriginal));

        when(profissionalSaudeGateway.updateProfissionalSaude(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProfissionalSaude atualizado = updateProfissionalSaude.execute(1, updateRequest);

        assertNotNull(atualizado);
        assertEquals("Dr. José Atualizado", atualizado.getNomeProfissionalSaude());
        assertEquals("jose.atualizado@hospital.com", atualizado.getEmailProfissionalSaude());
        assertEquals(EnumEspecialidadeProfissionalSaude.PSICOLOGO, atualizado.getEspecialidadeProfissionalSaude());

        verify(profissionalSaudeGateway).updateProfissionalSaude(any());
    }
}

