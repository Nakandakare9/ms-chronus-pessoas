package br.com.chronus.pessoas.application.domain;

import br.com.chronus.pessoas.application.enums.EnumParentesco;
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

class ContatoAnjoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarContatoAnjoValidoComBuilder() {
        ContatoAnjo contato = ContatoAnjo.builder()
                .nomeContatoAnjo("Maria Clara")
                .emailContatoAnjo("maria@exemplo.com")
                .cpfContatoAnjo("111.444.777-35")
                .telefoneContatoAnjo("11999998888")
                .parentescoContatoAnjo(EnumParentesco.IRMAO)
                .observacaoContatoAnjo("Contato confiável")
                .pacienteList(Collections.emptyList())
                .build();

        Set<ConstraintViolation<ContatoAnjo>> violations = validator.validate(contato);
        assertTrue(violations.isEmpty(), "ContatoAnjo deve ser válido");
    }

    @Test
    void deveFalharComEmailInvalido() {
        ContatoAnjo contato = ContatoAnjo.builder()
                .nomeContatoAnjo("Carlos")
                .emailContatoAnjo("email-invalido")
                .cpfContatoAnjo("111.444.777-35")
                .telefoneContatoAnjo("11999998888")
                .build();

        Set<ConstraintViolation<ContatoAnjo>> violations = validator.validate(contato);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailContatoAnjo")));
    }

    @Test
    void deveFalharComNomeInvalido() {
        ContatoAnjo contato = ContatoAnjo.builder()
                .nomeContatoAnjo("Maria123")
                .emailContatoAnjo("maria@exemplo.com")
                .cpfContatoAnjo("111.444.777-35")
                .telefoneContatoAnjo("11999998888")
                .build();

        Set<ConstraintViolation<ContatoAnjo>> violations = validator.validate(contato);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeContatoAnjo")));
    }

    @Test
    void deveCriarContatoAnjoComMetodoFactory() {
        ContatoAnjo contato = ContatoAnjo.createContatoAnjo(
                "Joana",
                "joana@teste.com",
                "111.444.777-35",
                "11999999999",
                EnumParentesco.MAE,
                "Contato de confiança",
                List.of()
        );

        assertEquals("Joana", contato.getNomeContatoAnjo());
        assertEquals(EnumParentesco.MAE, contato.getParentescoContatoAnjo());
    }
}
