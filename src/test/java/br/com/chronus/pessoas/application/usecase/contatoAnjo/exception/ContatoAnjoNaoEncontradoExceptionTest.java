package br.com.chronus.pessoas.application.usecase.contatoAnjo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContatoAnjoNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECodeCorretos() {
        Integer idContatoAnjo = 42;

        ContatoAnjoNaoEncontradoException ex = new ContatoAnjoNaoEncontradoException(idContatoAnjo);

        String mensagemEsperada = String.format("Contato Anjo com id [%s] não encontrado.", idContatoAnjo);
        String codigoEsperado = "contatoAnjoNaoEncontrado";

        assertEquals(mensagemEsperada, ex.getMessage());
        assertEquals(codigoEsperado, ex.getErrorCode());
    }
}
