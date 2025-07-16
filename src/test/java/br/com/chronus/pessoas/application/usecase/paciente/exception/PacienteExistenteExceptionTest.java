package br.com.chronus.pessoas.application.usecase.paciente.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacienteExistenteExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECodeCorretos() {
        Integer idPaciente = 123;
        String nomePaciente = "João Silva";

        PacienteExistenteException ex = new PacienteExistenteException(idPaciente, nomePaciente);

        String mensagemEsperada = String.format("Paciente [%s] já cadastrado com o ID [%s] informado.", idPaciente, nomePaciente);
        String codigoEsperado = "paciente.existente";

        assertEquals(mensagemEsperada, ex.getMessage());
        assertEquals(codigoEsperado, ex.getErrorCode());
    }
}
