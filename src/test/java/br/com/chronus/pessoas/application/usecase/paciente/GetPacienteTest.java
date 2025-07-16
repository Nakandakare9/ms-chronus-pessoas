package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
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

class GetPacienteTest {

    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private GetPaciente getPaciente;

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
                .telefonePaciente("11999998888")
                .dtNascPaciente(LocalDate.of(1990, 1, 1))
                .enderecoPaciente("Rua das Flores")
                .contatoAnjoList(null)
                .profissionalSaudeList(null)
                .build();
    }

    @Test
    void deveRetornarPacienteQuandoEncontrado() {
        Paciente paciente = gerarPacienteFake();

        when(pacienteGateway.getPacienteById(1)).thenReturn(Optional.of(paciente));

        Paciente resultado = getPaciente.execute(1);

        assertNotNull(resultado);
        assertEquals("João da Silva", resultado.getNomePaciente());
        verify(pacienteGateway).getPacienteById(1);
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoEncontrado() {
        when(pacienteGateway.getPacienteById(99)).thenReturn(Optional.empty());

        PacienteNaoEncontradoException ex = assertThrows(PacienteNaoEncontradoException.class, () -> {
            getPaciente.execute(99);
        });

        assertNotNull(ex.getMessage());
        System.out.println("Mensagem da exceção: " + ex.getMessage());

        assertTrue(ex.getMessage().toLowerCase().contains("não foi encontrado")
                        || ex.getMessage().toLowerCase().contains("paciente"),
                "A mensagem deve indicar que o paciente não foi encontrado.");

        verify(pacienteGateway, never()).createPaciente(any());
    }
}
