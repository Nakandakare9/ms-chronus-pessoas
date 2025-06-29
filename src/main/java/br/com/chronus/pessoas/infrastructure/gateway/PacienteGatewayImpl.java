package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ContatoAnjoEntity;
import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ProfissionalSaudeEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PacienteGatewayImpl implements PacienteGateway {

    private final PacienteRepository pacienteRepository;

    @Override
    public Paciente createPaciente(Paciente paciente) {
        PacienteEntity pacienteEntity = mapToEntity(paciente);
        PacienteEntity savedPacienteEntity = pacienteRepository.save(pacienteEntity);
        return mapToDomain(savedPacienteEntity);
    }

    @Override
    public Optional<Paciente> getPacienteById(final int idPaciente) {
        return pacienteRepository.findById(idPaciente)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Paciente> getPacienteByNome(final String nomePaciente) {
        return pacienteRepository.findByNomePaciente(nomePaciente)
                .map(this::mapToDomain);
    }

    @Override
    public Paciente updatePaciente(final Paciente paciente) {
        final var pacienteFound = pacienteRepository.findById(paciente.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Paciente não encontrado com o ID [%s] : ", paciente.getIdPaciente())));

        final var pacienteEntity = PacienteEntity.builder()
                .idPaciente(pacienteFound.getIdPaciente())
                .nomePaciente(paciente.getNomePaciente())
                .emailPaciente(paciente.getEmailPaciente())
                .cpfPaciente(paciente.getCpfPaciente())
                .telefonePaciente(paciente.getTelefonePaciente())
                .dtNascPaciente(paciente.getDtNascPaciente())
                .enderecoPaciente(paciente.getEnderecoPaciente())
                .contatoAnjoList(paciente.getContatoAnjoList().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .profissionalSaudeList(paciente.getProfissionalSaudeList().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .build();

        final var updatedPacienteEntity = pacienteRepository.save(pacienteEntity);

        return mapToDomain(updatedPacienteEntity);
    }

    @Transactional
    @Override
    public void deletePaciente(final int idPaciente) {
        final var pacienteEntity = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Paciente não encontrado com o ID [%s] : ", idPaciente)));

        pacienteRepository.delete(pacienteEntity);
    }

    public PacienteEntity mapToEntity(final Paciente paciente) {
        return PacienteEntity.builder()
                .idPaciente(paciente.getIdPaciente())
                .nomePaciente(paciente.getNomePaciente())
                .emailPaciente(paciente.getEmailPaciente())
                .cpfPaciente(paciente.getCpfPaciente())
                .telefonePaciente(paciente.getTelefonePaciente())
                .dtNascPaciente(paciente.getDtNascPaciente())
                .enderecoPaciente(paciente.getEnderecoPaciente())
                .contatoAnjoList(paciente.getContatoAnjoList() != null ?
                        paciente.getContatoAnjoList().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList()) : null)
                .profissionalSaudeList(paciente.getProfissionalSaudeList() != null ?
                        paciente.getProfissionalSaudeList().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public Paciente mapToDomain(final PacienteEntity pacienteEntity) {
        return Paciente.builder()
                .idPaciente(pacienteEntity.getIdPaciente())
                .nomePaciente(pacienteEntity.getNomePaciente())
                .emailPaciente(pacienteEntity.getEmailPaciente())
                .cpfPaciente(pacienteEntity.getCpfPaciente())
                .telefonePaciente(pacienteEntity.getTelefonePaciente())
                .dtNascPaciente(pacienteEntity.getDtNascPaciente())
                .enderecoPaciente(pacienteEntity.getEnderecoPaciente())
                .contatoAnjoList(pacienteEntity.getContatoAnjoList() != null ?
                        pacienteEntity.getContatoAnjoList().stream()
                                .map(this::mapToDomain)
                                .collect(Collectors.toList()) : null)
                .profissionalSaudeList(pacienteEntity.getProfissionalSaudeList() != null ?
                        pacienteEntity.getProfissionalSaudeList().stream()
                                .map(this::mapToDomain)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public ContatoAnjoEntity mapToEntity(final ContatoAnjo contatoAnjo) {
        return ContatoAnjoEntity.builder()
                .idContatoAnjo(contatoAnjo.getIdContatoAnjo())
                .nomeContatoAnjo(contatoAnjo.getNomeContatoAnjo())
                .emailContatoAnjo(contatoAnjo.getEmailContatoAnjo())
                .cpfContatoAnjo(contatoAnjo.getCpfContatoAnjo())
                .telefoneContatoAnjo(contatoAnjo.getTelefoneContatoAnjo())
                .parentescoContatoAnjo(contatoAnjo.getParentescoContatoAnjo())
                .observacaoContatoAnjo(contatoAnjo.getObservacaoContatoAnjo())
                .pacienteList(contatoAnjo.getPacienteList() != null ?
                        contatoAnjo.getPacienteList().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public ContatoAnjo mapToDomain(final ContatoAnjoEntity contatoAnjoEntity) {
        return ContatoAnjo.builder()
                .idContatoAnjo(contatoAnjoEntity.getIdContatoAnjo())
                .nomeContatoAnjo(contatoAnjoEntity.getNomeContatoAnjo())
                .emailContatoAnjo(contatoAnjoEntity.getEmailContatoAnjo())
                .cpfContatoAnjo(contatoAnjoEntity.getCpfContatoAnjo())
                .telefoneContatoAnjo(contatoAnjoEntity.getTelefoneContatoAnjo())
                .parentescoContatoAnjo(contatoAnjoEntity.getParentescoContatoAnjo())
                .observacaoContatoAnjo(contatoAnjoEntity.getObservacaoContatoAnjo())
                .pacienteList(contatoAnjoEntity.getPacienteList() != null ?
                        contatoAnjoEntity.getPacienteList().stream()
                                .map(this::mapToDomain)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public ProfissionalSaudeEntity mapToEntity(final ProfissionalSaude profissionalSaude) {
        return ProfissionalSaudeEntity.builder()
                .idProfissionalSaude(profissionalSaude.getIdProfissionalSaude())
                .nomeProfissionalSaude(profissionalSaude.getNomeProfissionalSaude())
                .emailProfissionalSaude(profissionalSaude.getEmailProfissionalSaude())
                .especialidadeProfissionalSaude(profissionalSaude.getEspecialidadeProfissionalSaude())
                .crmProfissionalSaude(profissionalSaude.getCrmProfissionalSaude())
                .build();
    }

    public ProfissionalSaude mapToDomain(final ProfissionalSaudeEntity profissionalSaudeEntity) {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(profissionalSaudeEntity.getIdProfissionalSaude())
                .nomeProfissionalSaude(profissionalSaudeEntity.getNomeProfissionalSaude())
                .especialidadeProfissionalSaude(profissionalSaudeEntity.getEspecialidadeProfissionalSaude())
                .emailProfissionalSaude(profissionalSaudeEntity.getEmailProfissionalSaude())
                .crmProfissionalSaude(profissionalSaudeEntity.getCrmProfissionalSaude())
                .build();
    }
}