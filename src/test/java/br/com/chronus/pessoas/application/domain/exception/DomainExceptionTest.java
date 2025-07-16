package br.com.chronus.pessoas.application.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECode() {
        String mensagem = "Erro de domínio";
        String codigo = "domain_error";

        DomainException ex = new DomainException(mensagem, codigo);

        assertEquals(mensagem, ex.getMessage());
        assertEquals(codigo, ex.getErrorCode());
    }

    @Test
    void deveCriarExcecaoComMensagemECodePadrao() {
        String mensagem = "Erro padrão";

        DomainException ex = new DomainException(mensagem);

        assertEquals(mensagem, ex.getMessage());
        assertEquals("domain_exception", ex.getErrorCode());
    }
}

