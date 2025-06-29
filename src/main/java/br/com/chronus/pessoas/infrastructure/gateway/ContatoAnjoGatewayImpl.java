package br.com.chronus.pessoas.infrastructure.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ContatoAnjoEntity;
import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import br.com.chronus.pessoas.infrastructure.persistence.repository.ContatoAnjoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContatoAnjoGatewayImpl implements ContatoAnjoGateway {

    private final ContatoAnjoRepository contatoAnjoRepository;

    @Override
    public ContatoAnjo createContatoAnjo(ContatoAnjo contatoAnjo) {
        ContatoAnjoEntity entity = mapToEntityContatoAnjo(contatoAnjo);
        ContatoAnjoEntity saved = contatoAnjoRepository.save(entity);
        return mapToDomainContatoAnjo(saved);
    }

    @Override
    public Optional<ContatoAnjo> getContatoAnjoById(final int idContatoAnjo) {
        return contatoAnjoRepository.findById(idContatoAnjo)
                .map(this::mapToDomainContatoAnjo);
    }

    @Override
    public Optional<ContatoAnjo> getContatoAnjoByNome(String nomeContatoAnjo) {
        return contatoAnjoRepository.findByNomeContatoAnjo(nomeContatoAnjo)
                .map(this::mapToDomainContatoAnjo);
    }

    @Override
    public List<ContatoAnjo> getContatoAnjoByPacienteId(final int idPaciente) {
        return contatoAnjoRepository.findByPacienteList_IdPaciente(idPaciente)
                .stream()
                .map(this::mapToDomainContatoAnjo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContatoAnjo> findAllContatoAnjo() {
        return contatoAnjoRepository.findAll().stream()
                .map(this::mapToDomainContatoAnjo)
                .collect(Collectors.toList());
    }

    @Override
    public ContatoAnjo updateContatoAnjo(ContatoAnjo contatoAnjo) {
        ContatoAnjoEntity found = contatoAnjoRepository.findById(contatoAnjo.getIdContatoAnjo())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("ContatoAnjo não encontrado com o ID [%s]", contatoAnjo.getIdContatoAnjo())));
        ContatoAnjoEntity entity = ContatoAnjoEntity.builder()
                .idContatoAnjo(found.getIdContatoAnjo())
                .nomeContatoAnjo(contatoAnjo.getNomeContatoAnjo())
                .emailContatoAnjo(contatoAnjo.getEmailContatoAnjo())
                .cpfContatoAnjo(contatoAnjo.getCpfContatoAnjo())
                .telefoneContatoAnjo(contatoAnjo.getTelefoneContatoAnjo())
                .parentescoContatoAnjo(contatoAnjo.getParentescoContatoAnjo())
                .observacaoContatoAnjo(contatoAnjo.getObservacaoContatoAnjo())
                .pacienteList(contatoAnjo.getPacienteList() != null ?
                        contatoAnjo.getPacienteList().stream()
                                .map(this::mapToEntityPaciente)
                                .collect(Collectors.toList()) : null)
                .build();
        ContatoAnjoEntity updated = contatoAnjoRepository.save(entity);
        return mapToDomainContatoAnjo(updated);
    }

    @Transactional
    @Override
    public void deleteContatoAnjo(final int idContatoAnjo) {
        ContatoAnjoEntity entity = contatoAnjoRepository.findById(idContatoAnjo)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("ContatoAnjo não encontrado com o ID [%s]", idContatoAnjo)));
        contatoAnjoRepository.delete(entity);
    }

    public ContatoAnjoEntity mapToEntityContatoAnjo(final ContatoAnjo contatoAnjo) {
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
                                .map(this::mapToEntityPaciente)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public ContatoAnjo mapToDomainContatoAnjo(final ContatoAnjoEntity entity) {
        return ContatoAnjo.builder()
                .idContatoAnjo(entity.getIdContatoAnjo())
                .nomeContatoAnjo(entity.getNomeContatoAnjo())
                .emailContatoAnjo(entity.getEmailContatoAnjo())
                .cpfContatoAnjo(entity.getCpfContatoAnjo())
                .telefoneContatoAnjo(entity.getTelefoneContatoAnjo())
                .parentescoContatoAnjo(entity.getParentescoContatoAnjo())
                .observacaoContatoAnjo(entity.getObservacaoContatoAnjo())
                .pacienteList(entity.getPacienteList() != null ?
                        entity.getPacienteList().stream()
                                .map(this::mapToDomainPaciente)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public PacienteEntity mapToEntityPaciente(final Paciente paciente) {
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

    public Paciente mapToDomainPaciente(final PacienteEntity entity) {
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