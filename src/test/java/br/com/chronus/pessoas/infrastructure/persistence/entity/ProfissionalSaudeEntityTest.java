package br.com.chronus.pessoas.infrastructure.persistence.entity;

import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfissionalSaudeEntityTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGettersAndSetters() {
        ProfissionalSaudeEntity profissional = new ProfissionalSaudeEntity();
        profissional.setIdProfissionalSaude(1);
        profissional.setNomeProfissionalSaude("Dr. Carlos Silva");
        profissional.setEmailProfissionalSaude("carlos.silva@email.com");
        profissional.setEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO);
        profissional.setCrmProfissionalSaude("123456");

        assertEquals(1, profissional.getIdProfissionalSaude());
        assertEquals("Dr. Carlos Silva", profissional.getNomeProfissionalSaude());
        assertEquals("carlos.silva@email.com", profissional.getEmailProfissionalSaude());
        assertEquals(EnumEspecialidadeProfissionalSaude.MEDICO, profissional.getEspecialidadeProfissionalSaude());
        assertEquals("123456", profissional.getCrmProfissionalSaude());
    }

    @Test
    void testBuilder() {
        ProfissionalSaudeEntity profissional = ProfissionalSaudeEntity.builder()
                .idProfissionalSaude(2)
                .nomeProfissionalSaude("Dra. Ana Pereira")
                .emailProfissionalSaude("ana.pereira@email.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.ENFERMEIRO)
                .crmProfissionalSaude("654321")
                .build();

        assertEquals(2, profissional.getIdProfissionalSaude());
        assertEquals("Dra. Ana Pereira", profissional.getNomeProfissionalSaude());
        assertEquals("ana.pereira@email.com", profissional.getEmailProfissionalSaude());
        assertEquals(EnumEspecialidadeProfissionalSaude.ENFERMEIRO, profissional.getEspecialidadeProfissionalSaude());
        assertEquals("654321", profissional.getCrmProfissionalSaude());
    }

    @Test
    void testJsonSerializationDeserialization() throws Exception {
        ProfissionalSaudeEntity profissional = ProfissionalSaudeEntity.builder()
                .idProfissionalSaude(3)
                .nomeProfissionalSaude("Dr. João Souza")
                .emailProfissionalSaude("joao.souza@email.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.FISIOTERAPEUTA)
                .crmProfissionalSaude("789123")
                .build();

        String json = objectMapper.writeValueAsString(profissional);
        assertNotNull(json);
        assertTrue(json.contains("\"nomeProfissionalSaude\":\"Dr. João Souza\""));
        assertTrue(json.contains("\"especialidadeProfissionalSaude\":\"FISIOTERAPEUTA\""));

        ProfissionalSaudeEntity profissionalDeserialized = objectMapper.readValue(json, ProfissionalSaudeEntity.class);
        assertEquals(profissional.getIdProfissionalSaude(), profissionalDeserialized.getIdProfissionalSaude());
        assertEquals(profissional.getNomeProfissionalSaude(), profissionalDeserialized.getNomeProfissionalSaude());
        assertEquals(profissional.getEmailProfissionalSaude(), profissionalDeserialized.getEmailProfissionalSaude());
        assertEquals(profissional.getEspecialidadeProfissionalSaude(), profissionalDeserialized.getEspecialidadeProfissionalSaude());
        assertEquals(profissional.getCrmProfissionalSaude(), profissionalDeserialized.getCrmProfissionalSaude());
    }

    @Test
    void testPacienteListSetterGetter() {
        ProfissionalSaudeEntity profissional = new ProfissionalSaudeEntity();

        List<PacienteEntity> pacientes = new ArrayList<>();
        PacienteEntity paciente = new PacienteEntity();
        paciente.setIdPaciente(100);
        paciente.setNomePaciente("Paciente Teste");
        pacientes.add(paciente);

        profissional.setPacienteList(pacientes);

        assertNotNull(profissional.getPacienteList());
        assertEquals(1, profissional.getPacienteList().size());
        assertEquals("Paciente Teste", profissional.getPacienteList().get(0).getNomePaciente());
    }
}
