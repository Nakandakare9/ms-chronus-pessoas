package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.dto.UpdatePacienteRequest;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePacienteTest {

    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private UpdatePaciente updatePaciente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Paciente gerarPacienteFake() {
        return Paciente.builder()
                .idPaciente(1)
                .nomePaciente("João da Silva")
                .emailPaciente("joao@teste.com")
                .cpfPaciente("944.662.490-83")
                .telefonePaciente("11999999999")
                .dtNascPaciente(LocalDate.of(1990, 1, 1))
                .enderecoPaciente("Rua das Flores")
                .contatoAnjoList(null)
                .profissionalSaudeList(null)
                .build();
    }

    private UpdatePacienteRequest gerarRequestAtualizacao() {
        UpdatePacienteRequest request = new UpdatePacienteRequest();
        request.setNomePaciente("João Atualizado");
        request.setEmailPaciente("joaoatualizado@teste.com");
        request.setTelefonePaciente("11988887777");
        request.setEnderecoPaciente("Rua Nova Atualizada");
        request.setContatoAnjoList(null);
        return request;
    }

    @Test
    void deveAtualizarPacienteComSucesso() {
        Paciente pacienteOriginal = gerarPacienteFake();
        UpdatePacienteRequest updateRequest = gerarRequestAtualizacao();

        when(pacienteGateway.getPacienteById(1)).thenReturn(Optional.of(pacienteOriginal));
        when(pacienteGateway.updatePaciente(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Paciente atualizado = updatePaciente.execute(1, updateRequest);

        assertNotNull(atualizado);
        assertEquals("João Atualizado", atualizado.getNomePaciente());
        assertEquals("joaoatualizado@teste.com", atualizado.getEmailPaciente());
        assertEquals("11988887777", atualizado.getTelefonePaciente());
        assertEquals("Rua Nova Atualizada", atualizado.getEnderecoPaciente());

        verify(pacienteGateway).updatePaciente(any(Paciente.class));
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoEncontrado() {
        when(pacienteGateway.getPacienteById(99)).thenReturn(Optional.empty());

        UpdatePacienteRequest request = gerarRequestAtualizacao();

        PacienteNaoEncontradoException ex = assertThrows(PacienteNaoEncontradoException.class, () -> {
            updatePaciente.execute(99, request);
        });

        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().toLowerCase().contains("não foi encontrado")
                || ex.getMessage().toLowerCase().contains("paciente"));

        verify(pacienteGateway, never()).updatePaciente(any());
    }
}
