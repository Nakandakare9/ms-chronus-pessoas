package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContatoAnjoControllerTest {

    @Mock
    private ContatoAnjoGateway contatoAnjoGateway;

    @InjectMocks
    private ContatoAnjoController contatoAnjoController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contatoAnjoController).build();
    }

    private ContatoAnjo createContatoAnjoFake() {
        ContatoAnjo contato = new ContatoAnjo();
        contato.setIdContatoAnjo(1);
        contato.setNomeContatoAnjo("Ana Silva");
        contato.setEmailContatoAnjo("ana@email.com");
        contato.setTelefoneContatoAnjo("11999999999");
        return contato;
    }

    @Test
    void deveCriarContatoAnjo() throws Exception {
        ContatoAnjo contato = createContatoAnjoFake();

        when(contatoAnjoGateway.createContatoAnjo(any(ContatoAnjo.class))).thenReturn(contato);

        mockMvc.perform(post("/chronus/contatos-anjo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contato)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idContatoAnjo").value(contato.getIdContatoAnjo()))
                .andExpect(jsonPath("$.nomeContatoAnjo").value(contato.getNomeContatoAnjo()));

        verify(contatoAnjoGateway).createContatoAnjo(any());
    }

    @Test
    void deveBuscarContatoAnjoPorIdEncontrado() throws Exception {
        ContatoAnjo contato = createContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoById(1)).thenReturn(Optional.of(contato));

        mockMvc.perform(get("/chronus/contatos-anjo/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeContatoAnjo").value("Ana Silva"));

        verify(contatoAnjoGateway).getContatoAnjoById(1);
    }

    @Test
    void deveRetornarNotFoundAoBuscarContatoAnjoPorIdNaoEncontrado() throws Exception {
        when(contatoAnjoGateway.getContatoAnjoById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/chronus/contatos-anjo/id/99"))
                .andExpect(status().isNotFound());

        verify(contatoAnjoGateway).getContatoAnjoById(99);
    }

    @Test
    void deveBuscarContatoAnjoPorNomeEncontrado() throws Exception {
        ContatoAnjo contato = createContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoByNome("Ana Silva")).thenReturn(Optional.of(contato));

        mockMvc.perform(get("/chronus/contatos-anjo/nome/Ana Silva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailContatoAnjo").value("ana@email.com"));

        verify(contatoAnjoGateway).getContatoAnjoByNome("Ana Silva");
    }

    @Test
    void deveRetornarNotFoundAoBuscarContatoAnjoPorNomeNaoEncontrado() throws Exception {
        when(contatoAnjoGateway.getContatoAnjoByNome("NomeInexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/chronus/contatos-anjo/nome/NomeInexistente"))
                .andExpect(status().isNotFound());

        verify(contatoAnjoGateway).getContatoAnjoByNome("NomeInexistente");
    }

    @Test
    void deveAtualizarContatoAnjo() throws Exception {
        ContatoAnjo contatoAtualizado = createContatoAnjoFake();
        contatoAtualizado.setNomeContatoAnjo("Ana Atualizada");

        when(contatoAnjoGateway.updateContatoAnjo(any(ContatoAnjo.class))).thenReturn(contatoAtualizado);

        mockMvc.perform(put("/chronus/contatos-anjo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contatoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeContatoAnjo").value("Ana Atualizada"));

        verify(contatoAnjoGateway).updateContatoAnjo(any());
    }

    @Test
    void deveDeletarContatoAnjo() throws Exception {
        doNothing().when(contatoAnjoGateway).deleteContatoAnjo(1);

        mockMvc.perform(delete("/chronus/contatos-anjo/1"))
                .andExpect(status().isNoContent());

        verify(contatoAnjoGateway).deleteContatoAnjo(1);
    }

    @Test
    void deveBuscarContatoAnjoPorPacienteIdEncontrado() throws Exception {
        ContatoAnjo contato = createContatoAnjoFake();

        when(contatoAnjoGateway.getContatoAnjoByPacienteId(1)).thenReturn(List.of(contato));

        mockMvc.perform(get("/chronus/contatos-anjo/paciente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idContatoAnjo").value(contato.getIdContatoAnjo()));

        verify(contatoAnjoGateway).getContatoAnjoByPacienteId(1);
    }

    @Test
    void deveRetornarNotFoundAoBuscarContatoAnjoPorPacienteIdSemResultados() throws Exception {
        when(contatoAnjoGateway.getContatoAnjoByPacienteId(1)).thenReturn(List.of());

        mockMvc.perform(get("/chronus/contatos-anjo/paciente/1"))
                .andExpect(status().isNotFound());

        verify(contatoAnjoGateway).getContatoAnjoByPacienteId(1);
    }

    @Test
    void deveRetornarContatoAnjosFindAll() throws Exception {
        ContatoAnjo contato = createContatoAnjoFake();

        when(contatoAnjoGateway.findAllContatoAnjo()).thenReturn(List.of(contato));

        mockMvc.perform(get("/chronus/contatos-anjo/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeContatoAnjo").value(contato.getNomeContatoAnjo()));

        verify(contatoAnjoGateway).findAllContatoAnjo();
    }

    @Test
    void deveRetornarNotFoundFindAllSemResultados() throws Exception {
        when(contatoAnjoGateway.findAllContatoAnjo()).thenReturn(List.of());

        mockMvc.perform(get("/chronus/contatos-anjo/findAll"))
                .andExpect(status().isNotFound());

        verify(contatoAnjoGateway).findAllContatoAnjo();
    }
}
