package br.com.chronus.pessoas.infrastructure.persistence.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PacienteEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @Test
    void testBuilderAndGetters() {
        PacienteEntity paciente = PacienteEntity.builder()
                .idPaciente(1)
                .nomePaciente("João da Silva")
                .emailPaciente("joao@email.com")
                .cpfPaciente("123.456.789-00")
                .telefonePaciente("11999999999")
                .dtNascPaciente(LocalDate.of(1990, 5, 20))
                .enderecoPaciente("Rua das Flores, 123")
                .contatoAnjoList(List.of())
                .profissionalSaudeList(List.of())
                .build();

        assertThat(paciente.getIdPaciente()).isEqualTo(1);
        assertThat(paciente.getNomePaciente()).isEqualTo("João da Silva");
        assertThat(paciente.getEmailPaciente()).isEqualTo("joao@email.com");
        assertThat(paciente.getCpfPaciente()).isEqualTo("123.456.789-00");
        assertThat(paciente.getTelefonePaciente()).isEqualTo("11999999999");
        assertThat(paciente.getDtNascPaciente()).isEqualTo(LocalDate.of(1990, 5, 20));
        assertThat(paciente.getEnderecoPaciente()).isEqualTo("Rua das Flores, 123");
        assertThat(paciente.getContatoAnjoList()).isEmpty();
        assertThat(paciente.getProfissionalSaudeList()).isEmpty();
    }

    @Test
    void testSetters() {
        PacienteEntity paciente = new PacienteEntity();

        paciente.setIdPaciente(2);
        paciente.setNomePaciente("Maria Oliveira");
        paciente.setEmailPaciente("maria@email.com");
        paciente.setCpfPaciente("987.654.321-00");
        paciente.setTelefonePaciente("11888888888");
        paciente.setDtNascPaciente(LocalDate.of(1985, 12, 10));
        paciente.setEnderecoPaciente("Av. Brasil, 456");
        paciente.setContatoAnjoList(List.of());
        paciente.setProfissionalSaudeList(List.of());

        assertThat(paciente.getIdPaciente()).isEqualTo(2);
        assertThat(paciente.getNomePaciente()).isEqualTo("Maria Oliveira");
        assertThat(paciente.getEmailPaciente()).isEqualTo("maria@email.com");
        assertThat(paciente.getCpfPaciente()).isEqualTo("987.654.321-00");
        assertThat(paciente.getTelefonePaciente()).isEqualTo("11888888888");
        assertThat(paciente.getDtNascPaciente()).isEqualTo(LocalDate.of(1985, 12, 10));
        assertThat(paciente.getEnderecoPaciente()).isEqualTo("Av. Brasil, 456");
        assertThat(paciente.getContatoAnjoList()).isEmpty();
        assertThat(paciente.getProfissionalSaudeList()).isEmpty();
    }

    @Test
    void testJsonSerializationDeserialization() throws Exception {
        PacienteEntity paciente = PacienteEntity.builder()
                .idPaciente(3)
                .nomePaciente("Carlos Eduardo")
                .emailPaciente("carlos@email.com")
                .cpfPaciente("555.666.777-88")
                .telefonePaciente("11666666666")
                .dtNascPaciente(LocalDate.of(1975, 7, 30))
                .enderecoPaciente("Rua Primavera, 789")
                .contatoAnjoList(List.of())
                .profissionalSaudeList(List.of())
                .build();

        String json = objectMapper.writeValueAsString(paciente);

        PacienteEntity pacienteFromJson = objectMapper.readValue(json, PacienteEntity.class);

        assertThat(pacienteFromJson.getNomePaciente()).isEqualTo("Carlos Eduardo");
        assertThat(pacienteFromJson.getCpfPaciente()).isEqualTo("555.666.777-88");
        assertThat(pacienteFromJson.getDtNascPaciente()).isEqualTo(LocalDate.of(1975, 7, 30));
        assertThat(pacienteFromJson.getEnderecoPaciente()).isEqualTo("Rua Primavera, 789");
    }

    @Test
    void testPersistenceEntity() {
        PacienteEntity paciente = PacienteEntity.builder()
                .nomePaciente("Ana Paula")
                .emailPaciente("ana@email.com")
                .cpfPaciente("222.333.444-55")
                .telefonePaciente("11555555555")
                .dtNascPaciente(LocalDate.of(1980, 3, 15))
                .enderecoPaciente("Av. Central, 101")
                .build();

        entityManager.persist(paciente);
        entityManager.flush();

        PacienteEntity found = entityManager.find(PacienteEntity.class, paciente.getIdPaciente());

        assertThat(found).isNotNull();
        assertThat(found.getNomePaciente()).isEqualTo("Ana Paula");
        assertThat(found.getCpfPaciente()).isEqualTo("222.333.444-55");
    }
}
