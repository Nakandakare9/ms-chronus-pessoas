package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.application.enums.EnumParentesco;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ContatoAnjoEntity;
import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContatoAnjoRepositoryTest {

    @Autowired
    private ContatoAnjoRepository contatoAnjoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve salvar e buscar contato anjo por nome")
    void salvarEBuscarPorNome() {
        ContatoAnjoEntity contato = ContatoAnjoEntity.builder()
                .nomeContatoAnjo("Maria Silva")
                .emailContatoAnjo("maria@email.com")
                .cpfContatoAnjo("123.456.789-00")
                .telefoneContatoAnjo("11999999999")
                .parentescoContatoAnjo(EnumParentesco.MAE)
                .observacaoContatoAnjo("Contato de emergência")
                .build();

        contatoAnjoRepository.save(contato);

        Optional<ContatoAnjoEntity> contatoBuscado = contatoAnjoRepository.findByNomeContatoAnjo("Maria Silva");

        assertThat(contatoBuscado).isPresent();
        assertThat(contatoBuscado.get().getNomeContatoAnjo()).isEqualTo("Maria Silva");
        assertThat(contatoBuscado.get().getParentescoContatoAnjo()).isEqualTo(EnumParentesco.MAE);
    }

    @Test
    void buscarPorIdPaciente() {

        PacienteEntity paciente = new PacienteEntity();
        paciente.setNomePaciente("João");
        paciente.setEmailPaciente("joao@email.com");
        paciente.setCpfPaciente("987.654.321-00");
        paciente.setTelefonePaciente("11988888888");
        paciente.setDtNascPaciente(LocalDate.of(1990, 1, 1));
        paciente.setEnderecoPaciente("Rua ABC, 123");

        paciente = pacienteRepository.save(paciente);


        ContatoAnjoEntity contato = ContatoAnjoEntity.builder()
                .nomeContatoAnjo("Carlos")
                .cpfContatoAnjo("111.222.333-44")
                .parentescoContatoAnjo(EnumParentesco.PAI)
                .pacienteList(List.of(paciente))
                .build();

        contatoAnjoRepository.save(contato);

        List<ContatoAnjoEntity> contatos = contatoAnjoRepository.findByPacienteList_IdPaciente(paciente.getIdPaciente());

        assertThat(contatos).isNotEmpty();
        assertThat(contatos.get(0).getNomeContatoAnjo()).isEqualTo("Carlos");
    }
}
