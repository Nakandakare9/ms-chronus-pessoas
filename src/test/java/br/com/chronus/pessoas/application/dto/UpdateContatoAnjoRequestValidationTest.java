package br.com.chronus.pessoas.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateContatoAnjoRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarCamposValidos() {
        UpdateContatoAnjoRequest dto = new UpdateContatoAnjoRequest();
        dto.setNomeContatoAnjo("Maria Silva");
        dto.setEmailContatoAnjo("maria@email.com");
        dto.setTelefoneContatoAnjo("11987654321");
        dto.setObservacaoContatoAnjo("Contato de confiança");
        dto.setPacienteList(List.of()); // pode ser lista vazia

        Set<ConstraintViolation<UpdateContatoAnjoRequest>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveDetectarCamposInvalidos() {
        UpdateContatoAnjoRequest dto = new UpdateContatoAnjoRequest();
        dto.setNomeContatoAnjo("Maria123"); // inválido (números)
        dto.setEmailContatoAnjo("emailinvalido"); // inválido
        dto.setTelefoneContatoAnjo("1234567890123456"); // maior que 15
        dto.setObservacaoContatoAnjo("A".repeat(256)); // maior que 255

        Set<ConstraintViolation<UpdateContatoAnjoRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeContatoAnjo")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailContatoAnjo")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("telefoneContatoAnjo")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("observacaoContatoAnjo")));
    }

    @Test
    void deveValidarNomeNaoNuloOuEmBranco() {
        UpdateContatoAnjoRequest dto = new UpdateContatoAnjoRequest();
        dto.setNomeContatoAnjo(""); // vazio
        dto.setEmailContatoAnjo("teste@email.com");

        Set<ConstraintViolation<UpdateContatoAnjoRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeContatoAnjo")));
    }

    @Test
    void deveValidarEmailNaoNuloOuEmBranco() {
        UpdateContatoAnjoRequest dto = new UpdateContatoAnjoRequest();
        dto.setNomeContatoAnjo("Maria");
        dto.setEmailContatoAnjo(""); // vazio

        Set<ConstraintViolation<UpdateContatoAnjoRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailContatoAnjo")));
    }
}
