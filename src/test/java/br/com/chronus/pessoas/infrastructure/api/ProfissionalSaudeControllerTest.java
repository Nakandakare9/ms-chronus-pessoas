package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfissionalSaudeControllerTest {

    @Mock
    private ProfissionalSaudeGateway profissionalSaudeGateway;

    @InjectMocks
    private ProfissionalSaudeController profissionalSaudeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profissionalSaudeController).build();
        objectMapper = new ObjectMapper();
    }

    private ProfissionalSaude createFakeProfissional() {
        return ProfissionalSaude.builder()
                .idProfissionalSaude(1)
                .nomeProfissionalSaude("Dr. João")
                .emailProfissionalSaude("joao@hospital.com")
                .crmProfissionalSaude("CRM-SP-1234")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .pacienteList(Collections.emptyList())
                .build();
    }

    @Test
    void deveCriarProfissional() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.createProfissionalSaude(any())).thenReturn(profissional);

        mockMvc.perform(post("/chronus/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeProfissionalSaude").value("Dr. João"));

        verify(profissionalSaudeGateway).createProfissionalSaude(any());
    }

    @Test
    void deveBuscarProfissionalPorId() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.getProfissionalSaudeById(1)).thenReturn(Optional.of(profissional));

        mockMvc.perform(get("/chronus/profissionais-saude/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmProfissionalSaude").value("CRM-SP-1234"));

        verify(profissionalSaudeGateway).getProfissionalSaudeById(1);
    }

    @Test
    void deveRetornarNotFoundAoBuscarProfissionalPorIdNaoExistente() throws Exception {
        when(profissionalSaudeGateway.getProfissionalSaudeById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/chronus/profissionais-saude/id/99"))
                .andExpect(status().isNotFound());

        verify(profissionalSaudeGateway).getProfissionalSaudeById(99);
    }

    @Test
    void deveBuscarProfissionalPorNome() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.getProfissionalSaudeByNome("Dr. João")).thenReturn(Optional.of(profissional));

        mockMvc.perform(get("/chronus/profissionais-saude/nome/Dr. João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailProfissionalSaude").value("joao@hospital.com"));

        verify(profissionalSaudeGateway).getProfissionalSaudeByNome("Dr. João");
    }

    @Test
    void deveBuscarProfissionalPorCrm() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.getProfissionalSaudeByCrm("CRM-SP-1234")).thenReturn(Optional.of(profissional));

        mockMvc.perform(get("/chronus/profissionais-saude/crm/CRM-SP-1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProfissionalSaude").value("Dr. João"));

        verify(profissionalSaudeGateway).getProfissionalSaudeByCrm("CRM-SP-1234");
    }

    @Test
    void deveBuscarPorEspecialidade() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.findByEspecialidade(EnumEspecialidadeProfissionalSaude.MEDICO))
                .thenReturn(List.of(profissional));

        mockMvc.perform(get("/chronus/profissionais-saude/especialidade/MEDICO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeProfissionalSaude").value("Dr. João"));

        verify(profissionalSaudeGateway).findByEspecialidade(EnumEspecialidadeProfissionalSaude.MEDICO);
    }

    @Test
    void deveBuscarTodosProfissionais() throws Exception {
        ProfissionalSaude profissional = createFakeProfissional();

        when(profissionalSaudeGateway.findAllProfissionalSaude()).thenReturn(List.of(profissional));

        mockMvc.perform(get("/chronus/profissionais-saude"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crmProfissionalSaude").value("CRM-SP-1234"));

        verify(profissionalSaudeGateway).findAllProfissionalSaude();
    }

    @Test
    void deveAtualizarProfissional() throws Exception {
        ProfissionalSaude profissionalAtualizado = createFakeProfissional();
        profissionalAtualizado.setNomeProfissionalSaude("Dr. João Atualizado");

        when(profissionalSaudeGateway.updateProfissionalSaude(any())).thenReturn(profissionalAtualizado);

        mockMvc.perform(put("/chronus/profissionais-saude/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profissionalAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProfissionalSaude").value("Dr. João Atualizado"));

        verify(profissionalSaudeGateway).updateProfissionalSaude(any());
    }

    @Test
    void deveDeletarProfissional() throws Exception {
        doNothing().when(profissionalSaudeGateway).deleteProfissionalSaude(1);

        mockMvc.perform(delete("/chronus/profissionais-saude/1"))
                .andExpect(status().isNoContent());

        verify(profissionalSaudeGateway).deleteProfissionalSaude(1);
    }
}
