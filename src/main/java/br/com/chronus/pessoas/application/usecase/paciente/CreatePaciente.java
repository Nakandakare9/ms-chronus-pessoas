package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteExistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePaciente {

    private final PacienteGateway pacienteGateway;

    public Paciente execute(final Paciente requestPaciente) {

        final var paciente = pacienteGateway.getPacienteById(requestPaciente.getIdPaciente());

       if (paciente.isPresent()) {
           throw new PacienteExistenteException(requestPaciente.getIdPaciente(), requestPaciente.getNomePaciente());
       }

       final var buildDomain =
               Paciente.createPaciente(
                       requestPaciente.getNomePaciente(),
                       requestPaciente.getEmailPaciente(),
                       requestPaciente.getCpfPaciente(),
                       requestPaciente.getTelefonePaciente(),
                       requestPaciente.getDtNascPaciente(),
                       requestPaciente.getEnderecoPaciente(),
                       requestPaciente.getContatoAnjoList(),
                       requestPaciente.getProfissionalSaudeList());

        return pacienteGateway.createPaciente(buildDomain);
    }
}
