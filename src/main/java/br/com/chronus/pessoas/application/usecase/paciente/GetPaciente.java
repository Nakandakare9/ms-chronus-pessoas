package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPaciente {

    private final PacienteGateway pacienteGateway;

    public Paciente execute(final Integer idPaciente) {
        return pacienteGateway.getPacienteById(idPaciente)
                .orElseThrow(() -> new PacienteNaoEncontradoException(idPaciente));
    }
}
