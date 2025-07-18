package br.com.chronus.pessoas.application.usecase.contatoAnjo.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class ContatoAnjoNaoEncontradoException extends BusinessException {

    private static final String ERROR_CODE = "contatoAnjoNaoEncontrado";
    private static final String ERROR_MESSAGE = "Contato Anjo com id [%s] não encontrado.";

    public ContatoAnjoNaoEncontradoException(final Integer idContatoAnjo) {
        super(format(ERROR_MESSAGE, idContatoAnjo), ERROR_CODE);
    }
}
