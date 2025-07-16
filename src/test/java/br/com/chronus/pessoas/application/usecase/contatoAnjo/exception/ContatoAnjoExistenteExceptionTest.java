package br.com.chronus.pessoas.application.usecase.contatoAnjo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContatoAnjoExistenteExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECodeCorretos() {
        Integer idContatoAnjo = 10;
        String nomeContatoAnjo = "Maria";

        ContatoAnjoExistenteException ex = new ContatoAnjoExistenteException(idContatoAnjo, nomeContatoAnjo);

        String mensagemEsperada = String.format("Contato Anjo [%s] já cadastrado com o ID [%s] informado.", nomeContatoAnjo, idContatoAnjo);
        String codigoEsperado = "contatoAnjo.existente";

        assertEquals(mensagemEsperada, ex.getMessage());
        assertEquals(codigoEsperado, ex.getErrorCode());
    }
}
