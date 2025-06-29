package br.com.chronus.pessoas.application.usecase.profissionalSaude.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

import static java.lang.String.format;

@Getter
public class ProfissionalSaudeExistenteException extends BusinessException {

    private static final String ERROR_CODE = "profissional.existente";
    private static final String ERROR_MESSAGE = "Profissional de Saúde [%s] já cadastrado com o ID [%s] informado.";

    public ProfissionalSaudeExistenteException(final Integer idProfissionalSaude, final String nomeProfissionalSaude) {
        super(format(ERROR_MESSAGE, idProfissionalSaude, nomeProfissionalSaude), ERROR_CODE);
    }
}
