package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.Paciente;

import java.util.Optional;
import java.util.UUID;

public interface PacienteGateway {

    Paciente createPaciente(final Paciente paciente);
    Optional<Paciente> getPacienteById(final UUID idPaciente);
    Paciente updatePaciente(final Paciente paciente);
    boolean deletePaciente(UUID idPaciente);

}
