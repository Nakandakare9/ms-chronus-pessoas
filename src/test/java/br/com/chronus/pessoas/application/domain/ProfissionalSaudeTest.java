package br.com.chronus.pessoas.application.domain;

import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfissionalSaudeTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarProfissionalValidoComBuilder() {
        ProfissionalSaude profissional = ProfissionalSaude.builder()
                .nomeProfissionalSaude("Dra Ana Paula")
                .emailProfissionalSaude("ana.paula@hospital.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO)
                .crmProfissionalSaude("123456/SP")
                .pacienteList(Collections.emptyList())
                .build();

        Set<ConstraintViolation<ProfissionalSaude>> violations = validator.validate(profissional);
        assertTrue(violations.isEmpty(), "ProfissionalSaude deve ser válido");
    }

    @Test
    void deveFalharComEmailInvalido() {
        ProfissionalSaude profissional = ProfissionalSaude.builder()
                .nomeProfissionalSaude("Dr João")
                .emailProfissionalSaude("joao.email-invalido")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.NUTRICIONISTA)
                .crmProfissionalSaude("789101/SP")
                .build();

        Set<ConstraintViolation<ProfissionalSaude>> violations = validator.validate(profissional);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailProfissionalSaude")));
    }

    @Test
    void deveFalharComNomeInvalido() {
        ProfissionalSaude profissional = ProfissionalSaude.builder()
                .nomeProfissionalSaude("Maria123") // inválido: contém números
                .emailProfissionalSaude("maria@teste.com")
                .especialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.PSICOLOGO)
                .crmProfissionalSaude("999999/SP")
                .build();

        Set<ConstraintViolation<ProfissionalSaude>> violations = validator.validate(profissional);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeProfissionalSaude")));
    }

    @Test
    void deveCriarProfissionalComMetodoFactory() {
        ProfissionalSaude profissional = ProfissionalSaude.createProfissionalSaude(
                "Carlos Silva",
                "carlos@ubs.gov.br",
                EnumEspecialidadeProfissionalSaude.FISIOTERAPEUTA,
                "CRM-SP-5555",
                List.of()
        );

        assertEquals("Carlos Silva", profissional.getNomeProfissionalSaude());
        assertEquals("CRM-SP-5555", profissional.getCrmProfissionalSaude());
        assertEquals(EnumEspecialidadeProfissionalSaude.FISIOTERAPEUTA, profissional.getEspecialidadeProfissionalSaude());
    }
}
