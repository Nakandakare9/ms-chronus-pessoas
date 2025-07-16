package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreatePacienteTest {

    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private CreatePaciente createPaciente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Paciente gerarPacienteFake() {
        return Paciente.builder()
                .idPaciente(1)
                .nomePaciente("Carlos Silva")
                .emailPaciente("carlos@teste.com")
                .cpfPaciente("111.444.777-35")
                .telefonePaciente("11999998888")
                .dtNascPaciente(LocalDate.of(1985, 1, 1))
                .enderecoPaciente("Rua das Palmeiras")
                .contatoAnjoList(null)
                .profissionalSaudeList(null)
                .build();
    }

    @Test
    void deveCriarPacienteQuandoNaoExiste() {
        Paciente novoPaciente = gerarPacienteFake();

        when(pacienteGateway.getPacienteById(novoPaciente.getIdPaciente())).thenReturn(Optional.empty());
        when(pacienteGateway.createPaciente(any())).thenReturn(novoPaciente);

        Paciente resultado = createPaciente.execute(novoPaciente);

        assertNotNull(resultado);
        assertEquals("Carlos Silva", resultado.getNomePaciente());
        verify(pacienteGateway).createPaciente(any(Paciente.class));
    }

    @Test
    void deveLancarExcecaoQuandoPacienteJaExiste() {
        Paciente pacienteExistente = gerarPacienteFake();

        when(pacienteGateway.getPacienteById(pacienteExistente.getIdPaciente()))
                .thenReturn(Optional.of(pacienteExistente));

        PacienteExistenteException ex = assertThrows(PacienteExistenteException.class, () -> {
            createPaciente.execute(pacienteExistente);
        });

        assertNotNull(ex.getMessage());
        System.out.println("Mensagem da exceção: " + ex.getMessage());

        assertTrue(ex.getMessage().toLowerCase().contains("paciente"),
                "Mensagem da exceção deve mencionar que o paciente já existe");

        verify(pacienteGateway, never()).createPaciente(any());
    }

}
