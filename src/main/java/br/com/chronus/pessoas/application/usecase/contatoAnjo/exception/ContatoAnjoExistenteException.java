package br.com.chronus.pessoas.application.usecase.contatoAnjo.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class ContatoAnjoExistenteException extends BusinessException {

    private static final String ERROR_CODE = "contatoAnjo.existente";
    private static final String ERROR_MESSAGE = "Contato Anjo [%s] já cadastrado com o ID [%s] informado.";

    public ContatoAnjoExistenteException(final Integer idContatoAnjo, final String nomeContatoAnjo) {
        super(format(ERROR_MESSAGE, nomeContatoAnjo, idContatoAnjo), ERROR_CODE);
    }
}
