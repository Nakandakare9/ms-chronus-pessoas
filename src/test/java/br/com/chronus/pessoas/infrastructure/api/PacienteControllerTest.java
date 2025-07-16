package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PacienteControllerTest {

    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private PacienteController pacienteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
    }


    private Paciente createPacienteFake() {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setNomePaciente("Maria Silva");
        paciente.setEmailPaciente("maria@email.com");
        paciente.setTelefonePaciente("11999999999");
        paciente.setEnderecoPaciente("Rua Exemplo, 123");
        paciente.setDtNascPaciente(LocalDate.of(1990, 1, 1));
        return paciente;
    }

    @Test
    void deveCriarPaciente() throws Exception {
        Paciente paciente = createPacienteFake();

        when(pacienteGateway.createPaciente(any(Paciente.class))).thenReturn(paciente);

        mockMvc.perform(post("/chronus/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPaciente").value(paciente.getIdPaciente()))
                .andExpect(jsonPath("$.nomePaciente").value(paciente.getNomePaciente()));

        verify(pacienteGateway).createPaciente(any());
    }

    @Test
    void deveBuscarPacientePorIdEncontrado() throws Exception {
        Paciente paciente = createPacienteFake();

        when(pacienteGateway.getPacienteById(1)).thenReturn(Optional.of(paciente));

        mockMvc.perform(get("/chronus/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomePaciente").value("Maria Silva"));

        verify(pacienteGateway).getPacienteById(1);
    }

    @Test
    void deveRetornarNotFoundAoBuscarPacientePorIdNaoEncontrado() throws Exception {
        when(pacienteGateway.getPacienteById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/chronus/pacientes/99"))
                .andExpect(status().isNotFound());

        verify(pacienteGateway).getPacienteById(99);
    }

    @Test
    void deveBuscarPacientePorNomeEncontrado() throws Exception {
        Paciente paciente = createPacienteFake();

        when(pacienteGateway.getPacienteByNome("Maria Silva")).thenReturn(Optional.of(paciente));

        mockMvc.perform(get("/chronus/pacientes/nome/Maria Silva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailPaciente").value("maria@email.com"));

        verify(pacienteGateway).getPacienteByNome("Maria Silva");
    }

    @Test
    void deveRetornarNotFoundAoBuscarPacientePorNomeNaoEncontrado() throws Exception {
        when(pacienteGateway.getPacienteByNome("NomeInexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/chronus/pacientes/nome/NomeInexistente"))
                .andExpect(status().isNotFound());

        verify(pacienteGateway).getPacienteByNome("NomeInexistente");
    }

    @Test
    void deveAtualizarPaciente() throws Exception {
        Paciente pacienteAtualizado = createPacienteFake();
        pacienteAtualizado.setNomePaciente("Maria Atualizada");

        when(pacienteGateway.updatePaciente(any(Paciente.class))).thenReturn(pacienteAtualizado);

        mockMvc.perform(put("/chronus/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pacienteAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomePaciente").value("Maria Atualizada"));

        verify(pacienteGateway).updatePaciente(any());
    }

    @Test
    void deveDeletarPaciente() throws Exception {
        doNothing().when(pacienteGateway).deletePaciente(1);

        mockMvc.perform(delete("/chronus/pacientes/1"))
                .andExpect(status().isNoContent());

        verify(pacienteGateway).deletePaciente(1);
    }
}

