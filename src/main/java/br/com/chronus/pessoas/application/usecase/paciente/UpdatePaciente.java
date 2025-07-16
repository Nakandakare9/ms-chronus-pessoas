package br.com.chronus.pessoas.application.usecase.paciente;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.dto.UpdatePacienteRequest;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.application.usecase.paciente.exception.PacienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePaciente {

    private final PacienteGateway pacienteGateway;

    public Paciente execute(final Integer idPaciente, final UpdatePacienteRequest updatePacienteRequest) {
         final var pacienteEncontrado = pacienteGateway.getPacienteById(idPaciente)
                .orElseThrow(() -> new PacienteNaoEncontradoException(idPaciente));

         pacienteEncontrado.setNomePaciente(updatePacienteRequest.getNomePaciente());
         pacienteEncontrado.setEmailPaciente(updatePacienteRequest.getEmailPaciente());
         pacienteEncontrado.setTelefonePaciente(updatePacienteRequest.getTelefonePaciente());
         pacienteEncontrado.setEnderecoPaciente(updatePacienteRequest.getEnderecoPaciente());
         pacienteEncontrado.setContatoAnjoList(updatePacienteRequest.getContatoAnjoList());

         return pacienteGateway.updatePaciente(pacienteEncontrado);

    }
}
