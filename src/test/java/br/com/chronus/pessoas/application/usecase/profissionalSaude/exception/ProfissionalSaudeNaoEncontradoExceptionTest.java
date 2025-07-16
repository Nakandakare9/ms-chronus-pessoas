package br.com.chronus.pessoas.application.usecase.profissionalSaude.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfissionalSaudeNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemEErrorCodeCorretos() {
        Integer id = 42;

        ProfissionalSaudeNaoEncontradoException exception =
                new ProfissionalSaudeNaoEncontradoException(id);

        String mensagemEsperada = String.format("Profissional de Saúde com id [%s] não encontrado.", id);
        String codigoEsperado = "profissional.nao.encontrado";

        assertEquals(mensagemEsperada, exception.getMessage());
        assertEquals(codigoEsperado, exception.getErrorCode());
    }
}

