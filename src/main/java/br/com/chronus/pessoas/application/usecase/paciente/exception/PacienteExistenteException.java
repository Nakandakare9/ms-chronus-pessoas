package br.com.chronus.pessoas.application.usecase.paciente.exception;

import br.com.chronus.pessoas.application.usecase.exception.BusinessException;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class PacienteExistenteException extends BusinessException {

    private static final String ERROR_CODE = "paciente.existente";
    private static final String ERROR_MESSAGE = "Paciente [%s] já cadastrado com o ID [%s] informado.";

    public PacienteExistenteException(final Integer idPaciente, final String nomePaciente) {
        super(format(ERROR_MESSAGE, idPaciente, nomePaciente), ERROR_CODE);
    }
}
