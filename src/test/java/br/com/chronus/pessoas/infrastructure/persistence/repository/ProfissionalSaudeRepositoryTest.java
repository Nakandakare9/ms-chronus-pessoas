package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.infrastructure.persistence.entity.ProfissionalSaudeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProfissionalSaudeRepositoryTest {

    @Autowired
    private ProfissionalSaudeRepository profissionalSaudeRepository;

    @Test
    @DisplayName("Deve salvar um profissional de saúde com sucesso")
    void deveSalvarProfissionalSaudeComSucesso() {
        ProfissionalSaudeEntity profissional = ProfissionalSaudeEntity.builder()
                .nomeProfissionalSaude("Dra. Ana Maria")
                .emailProfissionalSaude("ana.maria@hospital.com")
                .crmProfissionalSaude("CRM123")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .build();

        ProfissionalSaudeEntity salvo = profissionalSaudeRepository.save(profissional);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getIdProfissionalSaude()).isNotNull();
        assertThat(salvo.getNomeProfissionalSaude()).isEqualTo("Dra. Ana Maria");
    }

    @Test
    @DisplayName("Deve encontrar profissional de saúde por nome")
    void deveEncontrarProfissionalPorNome() {
        ProfissionalSaudeEntity profissional = ProfissionalSaudeEntity.builder()
                .nomeProfissionalSaude("Dr. Carlos")
                .emailProfissionalSaude("carlos@clinic.com")
                .crmProfissionalSaude("CRM456")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.ENFERMEIRO)
                .build();
        profissionalSaudeRepository.save(profissional);

        Optional<ProfissionalSaudeEntity> encontrado = profissionalSaudeRepository.findByNomeProfissionalSaude("Dr. Carlos");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCrmProfissionalSaude()).isEqualTo("CRM456");
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar profissional por nome")
    void deveRetornarVazioAoBuscarPorNomeInexistente() {
        Optional<ProfissionalSaudeEntity> encontrado = profissionalSaudeRepository.findByNomeProfissionalSaude("Nome Inexistente");
        assertThat(encontrado).isNotPresent();
    }

    @Test
    @DisplayName("Deve encontrar profissional de saúde por CRM")
    void deveEncontrarProfissionalPorCrm() {
        ProfissionalSaudeEntity profissional = ProfissionalSaudeEntity.builder()
                .nomeProfissionalSaude("Dra. Beatriz")
                .emailProfissionalSaude("beatriz@hospital.com")
                .crmProfissionalSaude("CRM789")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.FISIOTERAPEUTA)
                .build();
        profissionalSaudeRepository.save(profissional);

        Optional<ProfissionalSaudeEntity> encontrado = profissionalSaudeRepository.findByCrmProfissionalSaude("CRM789");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNomeProfissionalSaude()).isEqualTo("Dra. Beatriz");
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar profissional por CRM")
    void deveRetornarVazioAoBuscarPorCrmInexistente() {
        Optional<ProfissionalSaudeEntity> encontrado = profissionalSaudeRepository.findByCrmProfissionalSaude("CRM000");
        assertThat(encontrado).isNotPresent();
    }

    @Test
    @DisplayName("Deve encontrar profissionais por especialidade")
    void deveEncontrarPorEspecialidade() {
        ProfissionalSaudeEntity prof1 = ProfissionalSaudeEntity.builder()
                .nomeProfissionalSaude("Dra. Carla")
                .emailProfissionalSaude("carla@clinic.com")
                .crmProfissionalSaude("CRM101")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.PSIQUIATRA)
                .build();

        ProfissionalSaudeEntity prof2 = ProfissionalSaudeEntity.builder()
                .nomeProfissionalSaude("Dr. Felipe")
                .emailProfissionalSaude("felipe@clinic.com")
                .crmProfissionalSaude("CRM102")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.PSIQUIATRA)
                .build();

        profissionalSaudeRepository.save(prof1);
        profissionalSaudeRepository.save(prof2);

        List<ProfissionalSaudeEntity> psiquiatras = profissionalSaudeRepository.findByEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.PSIQUIATRA);

        assertThat(psiquiatras).isNotEmpty();
        assertThat(psiquiatras).hasSize(2);
        assertThat(psiquiatras).extracting("nomeProfissionalSaude").containsExactlyInAnyOrder("Dra. Carla", "Dr. Felipe");
    }

    @Test
    @DisplayName("Deve retornar lista vazia se não encontrar profissionais por especialidade")
    void deveRetornarListaVaziaParaEspecialidadeNaoEncontrada() {
        List<ProfissionalSaudeEntity> lista = profissionalSaudeRepository.findByEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.NUTRICIONISTA);
        assertThat(lista).isEmpty();
    }
}
