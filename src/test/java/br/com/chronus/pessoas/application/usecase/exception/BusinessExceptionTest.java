package br.com.chronus.pessoas.application.usecase.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECode() {
        String mensagem = "Erro de negócio";
        String codigo = "business_error";

        BusinessException ex = new BusinessException(mensagem, codigo);

        assertEquals(mensagem, ex.getMessage());
        assertEquals(codigo, ex.getErrorCode());
    }
}

