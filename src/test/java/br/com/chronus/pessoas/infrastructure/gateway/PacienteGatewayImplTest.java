package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PacienteGatewayImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteGatewayImpl pacienteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Paciente criarPacienteFake() {
        return Paciente.builder()
                .idPaciente(1)
                .nomePaciente("Maria Paciente")
                .emailPaciente("maria@teste.com")
                .cpfPaciente("12345678909")
                .telefonePaciente("11999999999")
                .dtNascPaciente(LocalDate.of(1990, 1, 1))
                .enderecoPaciente("Rua dos Testes, 123")
                .contatoAnjoList(null)
                .profissionalSaudeList(null)
                .build();
    }

    @Test
    void deveCriarPaciente() {
        Paciente paciente = criarPacienteFake();
        PacienteEntity entity = pacienteGateway.mapToEntity(paciente);

        when(pacienteRepository.save(any())).thenReturn(entity);

        Paciente resultado = pacienteGateway.createPaciente(paciente);

        assertNotNull(resultado);
        assertEquals("Maria Paciente", resultado.getNomePaciente());
        verify(pacienteRepository).save(any());
    }

    @Test
    void deveBuscarPacientePorId() {
        Paciente paciente = criarPacienteFake();
        PacienteEntity entity = pacienteGateway.mapToEntity(paciente);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(entity));

        Optional<Paciente> resultado = pacienteGateway.getPacienteById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Maria Paciente", resultado.get().getNomePaciente());
    }

    @Test
    void deveBuscarPacientePorNome() {
        Paciente paciente = criarPacienteFake();
        PacienteEntity entity = pacienteGateway.mapToEntity(paciente);

        when(pacienteRepository.findByNomePaciente("Maria Paciente")).thenReturn(Optional.of(entity));

        Optional<Paciente> resultado = pacienteGateway.getPacienteByNome("Maria Paciente");

        assertTrue(resultado.isPresent());
        assertEquals("Maria Paciente", resultado.get().getNomePaciente());
    }

    @Test
    void deveAtualizarPaciente() {
        Paciente paciente = criarPacienteFake();
        PacienteEntity entity = pacienteGateway.mapToEntity(paciente);

        paciente.setContatoAnjoList(new ArrayList<>());
        paciente.setProfissionalSaudeList(new ArrayList<>());

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(entity));
        when(pacienteRepository.save(any())).thenReturn(entity);

        Paciente resultado = pacienteGateway.updatePaciente(paciente);

        assertNotNull(resultado);
        assertEquals("Maria Paciente", resultado.getNomePaciente());
    }

    @Test
    void deveLancarExcecaoAoAtualizarPacienteInexistente() {
        Paciente paciente = criarPacienteFake();

        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> pacienteGateway.updatePaciente(paciente));

        assertTrue(ex.getMessage().contains("Paciente não encontrado com o ID"));
    }

    @Test
    void deveDeletarPaciente() {
        Paciente paciente = criarPacienteFake();
        PacienteEntity entity = pacienteGateway.mapToEntity(paciente);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(entity));
        doNothing().when(pacienteRepository).delete(entity);

        pacienteGateway.deletePaciente(1);

        verify(pacienteRepository).delete(entity);
    }

    @Test
    void deveLancarExcecaoAoDeletarPacienteInexistente() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pacienteGateway.deletePaciente(1));
    }
}
