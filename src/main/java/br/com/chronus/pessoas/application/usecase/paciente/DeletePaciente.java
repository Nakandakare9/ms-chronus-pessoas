package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePaciente {

    private final PacienteGateway pacienteGateway;

    public void execute(final Integer idPaciente) {
        final var paciente = pacienteGateway.getPacienteById(idPaciente)
                .orElseThrow(() -> new PacienteNaoEncontradoException(idPaciente));

        pacienteGateway.deletePaciente(paciente.getIdPaciente());
    }
}
