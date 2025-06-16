package br.com.chronus.pessoas.application.usecase.contatoAnjo.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

import static java.lang.String.format;

@Getter
public class ContatoAnjoExistenteException extends BusinessException {

    private static final String ERROR_CODE = "contatoAnjo.existente";
    private static final String ERROR_MESSAGE = "Contato Anjo [%s] já cadastrado com o ID [%s] informado.";

    public ContatoAnjoExistenteException(UUID idContatoAnjo, final String nomeContatoAnjo) {
        super(format(ERROR_MESSAGE, nomeContatoAnjo, idContatoAnjo), ERROR_CODE);
    }
}
