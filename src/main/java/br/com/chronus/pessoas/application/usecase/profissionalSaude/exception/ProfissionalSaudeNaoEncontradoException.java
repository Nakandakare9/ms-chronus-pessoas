package br.com.chronus.pessoas.application.usecase.profissionalSaude.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;

import static java.lang.String.format;

public class ProfissionalSaudeNaoEncontradoException extends BusinessException {

    private static final String ERROR_CODE = "profissional.nao.encontrado";
    private static final String ERROR_MESSAGE = "Profissional de Saúde com id [%s] não encontrado.";

    public ProfissionalSaudeNaoEncontradoException(final Integer idProfissionalSaude) {
        super(format(ERROR_MESSAGE, idProfissionalSaude), ERROR_CODE);
    }

}
