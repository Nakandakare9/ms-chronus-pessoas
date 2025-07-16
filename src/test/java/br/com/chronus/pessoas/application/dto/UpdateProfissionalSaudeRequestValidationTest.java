package br.com.chronus.pessoas.application.dto;

import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateProfissionalSaudeRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarCamposValidos() {
        UpdateProfissionalSaudeRequest dto = new UpdateProfissionalSaudeRequest();
        dto.setNomeProfissionalSaude("Ana Maria");
        dto.setEmailProfissionalSaude("ana.maria@email.com");
        dto.setEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.ENFERMEIRO);

        Set<ConstraintViolation<UpdateProfissionalSaudeRequest>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveDetectarCamposInvalidos() {
        UpdateProfissionalSaudeRequest dto = new UpdateProfissionalSaudeRequest();
        dto.setNomeProfissionalSaude("Ana123"); // Inválido: números não permitidos
        dto.setEmailProfissionalSaude("emailinvalido"); // Inválido
        dto.setEspecialidadeProfissionalSaude(null); // Pode ser válido, mas sem restrição @NotNull

        Set<ConstraintViolation<UpdateProfissionalSaudeRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeProfissionalSaude")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailProfissionalSaude")));
    }

    @Test
    void deveValidarNomeNaoNuloOuEmBranco() {
        UpdateProfissionalSaudeRequest dto = new UpdateProfissionalSaudeRequest();
        dto.setNomeProfissionalSaude(""); // vazio
        dto.setEmailProfissionalSaude("teste@email.com");
        dto.setEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.MEDICO);

        Set<ConstraintViolation<UpdateProfissionalSaudeRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeProfissionalSaude")));
    }

    @Test
    void deveValidarEmailNaoNuloOuEmBranco() {
        UpdateProfissionalSaudeRequest dto = new UpdateProfissionalSaudeRequest();
        dto.setNomeProfissionalSaude("Ana Maria");
        dto.setEmailProfissionalSaude(""); // vazio
        dto.setEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude.FISIOTERAPEUTA);

        Set<ConstraintViolation<UpdateProfissionalSaudeRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailProfissionalSaude")));
    }
}

