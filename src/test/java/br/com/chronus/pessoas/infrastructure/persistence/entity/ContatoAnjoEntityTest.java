package br.com.chronus.pessoas.infrastructure.persistence.entity;

import br.com.chronus.pessoas.application.enums.EnumParentesco;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ContatoAnjoEntityTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGettersAndSetters() {
        ContatoAnjoEntity contato = new ContatoAnjoEntity();
        contato.setIdContatoAnjo(1);
        contato.setNomeContatoAnjo("Maria Silva");
        contato.setEmailContatoAnjo("maria@email.com");
        contato.setCpfContatoAnjo("123.456.789-00");
        contato.setTelefoneContatoAnjo("11999999999");
        contato.setParentescoContatoAnjo(EnumParentesco.PAI);
        contato.setObservacaoContatoAnjo("Contato confiável");

        assertEquals(1, contato.getIdContatoAnjo());
        assertEquals("Maria Silva", contato.getNomeContatoAnjo());
        assertEquals("maria@email.com", contato.getEmailContatoAnjo());
        assertEquals("123.456.789-00", contato.getCpfContatoAnjo());
        assertEquals("11999999999", contato.getTelefoneContatoAnjo());
        assertEquals(EnumParentesco.PAI, contato.getParentescoContatoAnjo());
        assertEquals("Contato confiável", contato.getObservacaoContatoAnjo());
    }

    @Test
    void testBuilder() {
        ContatoAnjoEntity contato = ContatoAnjoEntity.builder()
                .idContatoAnjo(2)
                .nomeContatoAnjo("João Pereira")
                .emailContatoAnjo("joao@email.com")
                .cpfContatoAnjo("987.654.321-00")
                .telefoneContatoAnjo("11988888888")
                .parentescoContatoAnjo(EnumParentesco.MAE)
                .observacaoContatoAnjo("Amigo da família")
                .build();

        assertEquals(2, contato.getIdContatoAnjo());
        assertEquals("João Pereira", contato.getNomeContatoAnjo());
        assertEquals("joao@email.com", contato.getEmailContatoAnjo());
        assertEquals("987.654.321-00", contato.getCpfContatoAnjo());
        assertEquals("11988888888", contato.getTelefoneContatoAnjo());
        assertEquals(EnumParentesco.MAE, contato.getParentescoContatoAnjo());
        assertEquals("Amigo da família", contato.getObservacaoContatoAnjo());
    }

    @Test
    void testJsonSerializationDeserialization() throws Exception {
        ContatoAnjoEntity contato = ContatoAnjoEntity.builder()
                .idContatoAnjo(3)
                .nomeContatoAnjo("Ana Costa")
                .emailContatoAnjo("ana@email.com")
                .cpfContatoAnjo("111.222.333-44")
                .telefoneContatoAnjo("11977777777")
                .parentescoContatoAnjo(EnumParentesco.OUTRO)
                .observacaoContatoAnjo("Vizinha")
                .build();

        String json = objectMapper.writeValueAsString(contato);
        assertNotNull(json);
        assertTrue(json.contains("\"nomeContatoAnjo\":\"Ana Costa\""));
        assertTrue(json.contains("\"parentescoContatoAnjo\":\"OUTRO\""));

        ContatoAnjoEntity contatoDeserialized = objectMapper.readValue(json, ContatoAnjoEntity.class);
        assertEquals(contato.getIdContatoAnjo(), contatoDeserialized.getIdContatoAnjo());
        assertEquals(contato.getNomeContatoAnjo(), contatoDeserialized.getNomeContatoAnjo());
        assertEquals(contato.getEmailContatoAnjo(), contatoDeserialized.getEmailContatoAnjo());
        assertEquals(contato.getCpfContatoAnjo(), contatoDeserialized.getCpfContatoAnjo());
        assertEquals(contato.getTelefoneContatoAnjo(), contatoDeserialized.getTelefoneContatoAnjo());
        assertEquals(contato.getParentescoContatoAnjo(), contatoDeserialized.getParentescoContatoAnjo());
        assertEquals(contato.getObservacaoContatoAnjo(), contatoDeserialized.getObservacaoContatoAnjo());
    }

    @Test
    void testPacienteListSetterGetter() {
        ContatoAnjoEntity contato = new ContatoAnjoEntity();

        // Simulação simples - cria lista vazia
        List<PacienteEntity> pacientes = new ArrayList<>();
        PacienteEntity paciente = new PacienteEntity();
        paciente.setIdPaciente(100);
        paciente.setNomePaciente("Paciente Teste");
        pacientes.add(paciente);

        contato.setPacienteList(pacientes);

        assertNotNull(contato.getPacienteList());
        assertEquals(1, contato.getPacienteList().size());
        assertEquals("Paciente Teste", contato.getPacienteList().get(0).getNomePaciente());
    }
}
