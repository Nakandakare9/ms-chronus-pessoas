package br.com.chronus.pessoas.application.dto;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdatePacienteRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarCamposValidos() {
        UpdatePacienteRequest dto = new UpdatePacienteRequest();
        dto.setNomePaciente("Joao da Silva");
        dto.setEmailPaciente("joao.silva@email.com");
        dto.setTelefonePaciente("11987654321");
        dto.setEnderecoPaciente("Rua das Flores, 123");
        dto.setContatoAnjoList(List.of(new ContatoAnjo()));

        Set<ConstraintViolation<UpdatePacienteRequest>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveDetectarCamposInvalidos() {
        UpdatePacienteRequest dto = new UpdatePacienteRequest();
        dto.setNomePaciente("João123"); // inválido (números não permitidos)
        dto.setEmailPaciente("emailinvalido"); // inválido
        dto.setTelefonePaciente("1234567890123456"); // maior que 15 caracteres
        // enderecoPaciente e contatoAnjoList são opcionais

        Set<ConstraintViolation<UpdatePacienteRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomePaciente")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailPaciente")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("telefonePaciente")));
    }

    @Test
    void deveValidarNomeNaoNuloOuEmBranco() {
        UpdatePacienteRequest dto = new UpdatePacienteRequest();
        dto.setNomePaciente(""); // em branco
        dto.setEmailPaciente("teste@email.com");

        Set<ConstraintViolation<UpdatePacienteRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomePaciente")));
    }

    @Test
    void deveValidarEmailNaoNuloOuEmBranco() {
        UpdatePacienteRequest dto = new UpdatePacienteRequest();
        dto.setNomePaciente("Maria");
        dto.setEmailPaciente(""); // em branco

        Set<ConstraintViolation<UpdatePacienteRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailPaciente")));
    }
}

