package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPaciente {

    private final PacienteGateway pacienteGateway;

    public Paciente execute(final UUID idPaciente) {
        return pacienteGateway.getPacienteById(idPaciente)
                .orElseThrow(() -> new PacienteNaoEncontradoException(idPaciente));
    }
}
