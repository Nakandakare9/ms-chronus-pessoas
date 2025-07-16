package br.com.chronus.pessoas.application.usecase.paciente.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacienteNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECodeCorretos() {
        Integer idPaciente = 99;

        PacienteNaoEncontradoException ex = new PacienteNaoEncontradoException(idPaciente);

        String mensagemEsperada = String.format("Paciente com id [%s] não encontrado.", idPaciente);
        String codigoEsperado = "paciente.nao.encontrado";

        assertEquals(mensagemEsperada, ex.getMessage());
        assertEquals(codigoEsperado, ex.getErrorCode());
    }
}
