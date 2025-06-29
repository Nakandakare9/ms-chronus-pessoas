package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ProfissionalSaudeEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.ProfissionalSaudeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfissionalSaudeGatewayImpl implements ProfissionalSaudeGateway {

    private final ProfissionalSaudeRepository profissionalSaudeRepository;

    @Override
    public ProfissionalSaude createProfissionalSaude(ProfissionalSaude profissionalSaude) {
        ProfissionalSaudeEntity entity = mapToEntity(profissionalSaude);
        ProfissionalSaudeEntity saved = profissionalSaudeRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<ProfissionalSaude> getProfissionalSaudeById(int idProfissionalSaude) {
        return profissionalSaudeRepository.findById(idProfissionalSaude)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<ProfissionalSaude> getProfissionalSaudeByNome(String nomeProfissionalSaude) {
        return profissionalSaudeRepository.findByNomeProfissionalSaude(nomeProfissionalSaude)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<ProfissionalSaude> getProfissionalSaudeByCrm(String crmProfissionalSaude) {
        return profissionalSaudeRepository.findByCrmProfissionalSaude(crmProfissionalSaude)
                .map(this::mapToDomain);
    }

    @Override
    public List<ProfissionalSaude> findByEspecialidade(EnumEspecialidadeProfissionalSaude especialidade) {
        return profissionalSaudeRepository.findByEspecialidadeProfissionalSaude(especialidade)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalSaude> findAllProfissionalSaude() {
        return profissionalSaudeRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ProfissionalSaude updateProfissionalSaude(ProfissionalSaude profissionalSaude) {
        ProfissionalSaudeEntity found = profissionalSaudeRepository.findById(profissionalSaude.getIdProfissionalSaude())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Profissional Saude não encontrado com o ID [%s]", profissionalSaude.getIdProfissionalSaude())));
        ProfissionalSaudeEntity entity = ProfissionalSaudeEntity.builder()
                .idProfissionalSaude(found.getIdProfissionalSaude())
                .nomeProfissionalSaude(profissionalSaude.getNomeProfissionalSaude())
                .emailProfissionalSaude(profissionalSaude.getEmailProfissionalSaude())
                .especialidadeProfissionalSaude(profissionalSaude.getEspecialidadeProfissionalSaude())
                .crmProfissionalSaude(profissionalSaude.getCrmProfissionalSaude())
                .pacienteList(profissionalSaude.getPacienteList() != null ?
                        profissionalSaude.getPacienteList().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList()) : null)
                .build();
        ProfissionalSaudeEntity updated = profissionalSaudeRepository.save(entity);
        return mapToDomain(updated);
    }

    @Transactional
    @Override
    public void deleteProfissionalSaude(int idProfissionalSaude) {
        ProfissionalSaudeEntity entity = profissionalSaudeRepository.findById(idProfissionalSaude)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Profissional Saude não encontrado com o ID [%s]", idProfissionalSaude)));
        profissionalSaudeRepository.delete(entity);
    }

    public ProfissionalSaudeEntity mapToEntity(final ProfissionalSaude profissionalSaude) {
        return ProfissionalSaudeEntity.builder()
                .idProfissionalSaude(profissionalSaude.getIdProfissionalSaude())
                .nomeProfissionalSaude(profissionalSaude.getNomeProfissionalSaude())
                .emailProfissionalSaude(profissionalSaude.getEmailProfissionalSaude())
                .especialidadeProfissionalSaude(profissionalSaude.getEspecialidadeProfissionalSaude())
                .crmProfissionalSaude(profissionalSaude.getCrmProfissionalSaude())
                .pacienteList(profissionalSaude.getPacienteList() != null ?
                        profissionalSaude.getPacienteList().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public ProfissionalSaude mapToDomain(final ProfissionalSaudeEntity entity) {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(entity.getIdProfissionalSaude())
                .nomeProfissionalSaude(entity.getNomeProfissionalSaude())
                .emailProfissionalSaude(entity.getEmailProfissionalSaude())
                .especialidadeProfissionalSaude(entity.getEspecialidadeProfissionalSaude())
                .crmProfissionalSaude(entity.getCrmProfissionalSaude())
                .pacienteList(entity.getPacienteList() != null ?
                        entity.getPacienteList().stream()
                                .map(this::mapToDomain)
                                .collect(Collectors.toList()) : null)
                .build();
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
                .build();
    }

    public Paciente mapToDomain(final PacienteEntity entity) {
        return Paciente.builder()
                .idPaciente(entity.getIdPaciente())
                .nomePaciente(entity.getNomePaciente())
                .emailPaciente(entity.getEmailPaciente())
                .cpfPaciente(entity.getCpfPaciente())
                .telefonePaciente(entity.getTelefonePaciente())
                .dtNascPaciente(entity.getDtNascPaciente())
                .enderecoPaciente(entity.getEnderecoPaciente())
                .build();
    }
}