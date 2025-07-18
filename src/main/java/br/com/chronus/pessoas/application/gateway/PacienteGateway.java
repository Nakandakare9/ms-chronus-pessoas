package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.Paciente;

import java.util.Optional;

public interface PacienteGateway {

    Paciente createPaciente(final Paciente paciente);
    Optional<Paciente> getPacienteById(final int idPaciente);
    Optional<Paciente> getPacienteByNome(final String nomePaciente);
    Paciente updatePaciente(final Paciente paciente);
    void deletePaciente(final int idPaciente);

}
