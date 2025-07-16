package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ProfissionalSaudeEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.ProfissionalSaudeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfissionalSaudeGatewayImplTest {

    @Mock
    private ProfissionalSaudeRepository profissionalSaudeRepository;

    @InjectMocks
    private ProfissionalSaudeGatewayImpl gateway;

    private ProfissionalSaude profissional;
    private ProfissionalSaudeEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Paciente paciente = Paciente.builder()
                .idPaciente(10)
                .nomePaciente("Maria Souza")
                .emailPaciente("maria@example.com")
                .cpfPaciente("12345678900")
                .telefonePaciente("11999999999")
                .dtNascPaciente(LocalDate.of(1985, 5, 20))
                .enderecoPaciente("Rua das Flores, 100")
                .build();

        profissional = ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("João Silva")
                .emailProfissionalSaude("joao.silva@exemplo.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)  // Corrigido
                .crmProfissionalSaude("123456-SP")
                .pacienteList(List.of(paciente))
                .build();

        entity = gateway.mapToEntity(profissional);
    }

    @Test
    void deveCriarProfissional() {
        when(profissionalSaudeRepository.save(any())).thenReturn(entity);
        ProfissionalSaude result = gateway.createProfissionalSaude(profissional);
        assertNotNull(result);
        assertEquals("João Silva", result.getNomeProfissionalSaude());
    }

    @Test
    void deveBuscarPorId() {
        when(profissionalSaudeRepository.findById(1)).thenReturn(Optional.of(entity));
        Optional<ProfissionalSaude> result = gateway.getProfissionalSaudeById(1);
        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getNomeProfissionalSaude());
    }

    @Test
    void deveBuscarPorNome() {
        when(profissionalSaudeRepository.findByNomeProfissionalSaude("João Silva")).thenReturn(Optional.of(entity));
        Optional<ProfissionalSaude> result = gateway.getProfissionalSaudeByNome("João Silva");
        assertTrue(result.isPresent());
        assertEquals("123456-SP", result.get().getCrmProfissionalSaude());
    }

    @Test
    void deveBuscarPorCrm() {
        when(profissionalSaudeRepository.findByCrmProfissionalSaude("123456-SP")).thenReturn(Optional.of(entity));
        Optional<ProfissionalSaude> result = gateway.getProfissionalSaudeByCrm("123456-SP");
        assertTrue(result.isPresent());
        assertEquals(EnumEspecialidadeProfissionalSaude.MEDICO, result.get().getEspecialidadeProfissionalSaude());
    }

    @Test
    void deveBuscarPorEspecialidade() {
        when(profissionalSaudeRepository.findByEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO))
                .thenReturn(List.of(entity));
        List<ProfissionalSaude> result = gateway.findByEspecialidade(EnumEspecialidadeProfissionalSaude.MEDICO);
        assertEquals(1, result.size());
        assertEquals("João Silva", result.get(0).getNomeProfissionalSaude());
    }

    @Test
    void deveBuscarTodos() {
        when(profissionalSaudeRepository.findAll()).thenReturn(List.of(entity));
        List<ProfissionalSaude> result = gateway.findAllProfissionalSaude();
        assertEquals(1, result.size());
    }

    @Test
    void deveAtualizarProfissional() {
        when(profissionalSaudeRepository.findById(1)).thenReturn(Optional.of(entity));
        when(profissionalSaudeRepository.save(any())).thenReturn(entity);

        ProfissionalSaude updated = gateway.updateProfissionalSaude(profissional);
        assertNotNull(updated);
        assertEquals("João Silva", updated.getNomeProfissionalSaude());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProfissionalNaoEncontrado() {
        when(profissionalSaudeRepository.findById(99)).thenReturn(Optional.empty());
        profissional.setIdProfissionalSaude(99);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gateway.updateProfissionalSaude(profissional));
        assertTrue(ex.getMessage().contains("Profissional Saude não encontrado com o ID [99]"));
    }

    @Test
    void deveDeletarProfissional() {
        when(profissionalSaudeRepository.findById(1)).thenReturn(Optional.of(entity));
        doNothing().when(profissionalSaudeRepository).delete(entity);
        gateway.deleteProfissionalSaude(1);
        verify(profissionalSaudeRepository).delete(entity);
    }

    @Test
    void deveLancarExcecaoAoDeletarProfissionalNaoEncontrado() {
        when(profissionalSaudeRepository.findById(99)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gateway.deleteProfissionalSaude(99));
        assertTrue(ex.getMessage().contains("Profissional Saude não encontrado com o ID [99]"));
    }
}
