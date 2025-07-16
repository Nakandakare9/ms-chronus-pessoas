package br.com.chronus.pessoas.application.usecase.profissionalSaude.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfissionalSaudeExistenteExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemEErrorCodeCorretos() {
        Integer id = 123;
        String nome = "Dra. Ana";

        ProfissionalSaudeExistenteException exception =
                new ProfissionalSaudeExistenteException(id, nome);

        String mensagemEsperada = String.format("Profissional de Saúde [%s] já cadastrado com o ID [%s] informado.", id, nome);
        String codigoEsperado = "profissional.existente";

        assertEquals(mensagemEsperada, exception.getMessage());
        assertEquals(codigoEsperado, exception.getErrorCode());
    }
}

