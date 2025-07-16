package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve salvar um paciente com sucesso")
    void deveSalvarPacienteComSucesso() {
        PacienteEntity paciente = new PacienteEntity();
        paciente.setNomePaciente("Maria Silva");
        paciente.setEmailPaciente("maria.silva@email.com");
        paciente.setCpfPaciente("123.456.789-00");
        paciente.setTelefonePaciente("11999999999");
        paciente.setDtNascPaciente(LocalDate.of(1980, 5, 10));
        paciente.setEnderecoPaciente("Rua das Flores, 100");

        PacienteEntity salvo = pacienteRepository.save(paciente);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getIdPaciente()).isNotNull();
        assertThat(salvo.getNomePaciente()).isEqualTo("Maria Silva");
    }

    @Test
    @DisplayName("Deve encontrar paciente por nome")
    void deveEncontrarPacientePorNome() {

        PacienteEntity paciente = new PacienteEntity();
        paciente.setNomePaciente("João Santos");
        paciente.setEmailPaciente("joao.santos@email.com");
        paciente.setCpfPaciente("987.654.321-00");
        paciente.setTelefonePaciente("11888888888");
        paciente.setDtNascPaciente(LocalDate.of(1990, 3, 15));
        paciente.setEnderecoPaciente("Av. Central, 200");

        pacienteRepository.save(paciente);

        Optional<PacienteEntity> encontrado = pacienteRepository.findByNomePaciente("João Santos");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmailPaciente()).isEqualTo("joao.santos@email.com");
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar paciente pelo nome")
    void deveRetornarVazioQuandoNaoEncontrarPorNome() {
        Optional<PacienteEntity> encontrado = pacienteRepository.findByNomePaciente("Nome Inexistente");

        assertThat(encontrado).isNotPresent();
    }
}
