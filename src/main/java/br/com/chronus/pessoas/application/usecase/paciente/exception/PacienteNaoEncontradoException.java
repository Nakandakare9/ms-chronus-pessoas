package br.com.chronus.pessoas.application.usecase.paciente.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;

import static java.lang.String.format;

public class PacienteNaoEncontradoException extends BusinessException {

    private static final String ERROR_CODE = "paciente.nao.encontrado";
    private static final String ERROR_MESSAGE = "Paciente com id [%s] não encontrado.";

    public PacienteNaoEncontradoException(final Integer idPaciente) {
        super(format(ERROR_MESSAGE, idPaciente), ERROR_CODE);
    }
}

