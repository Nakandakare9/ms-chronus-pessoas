package br.com.chronus.pessoas.application.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarPacienteValidoComBuilder() {
        Paciente paciente = Paciente.builder()
                .nomePaciente("Joao da Silva")
                .emailPaciente("joao@email.com")
                .cpfPaciente("111.444.777-35") // válido
                .telefonePaciente("11999999999")
                .dtNascPaciente(LocalDate.of(1990, 1, 1))
                .enderecoPaciente("Rua das Flores")
                .contatoAnjoList(Collections.emptyList())
                .profissionalSaudeList(Collections.emptyList())
                .build();

        Set<ConstraintViolation<Paciente>> violacoes = validator.validate(paciente);
        violacoes.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
        assertTrue(violacoes.isEmpty(), "Paciente deve ser válido");
    }



    @Test
    void deveFalharComEmailInvalido() {
        Paciente paciente = Paciente.builder()
                .nomePaciente("Maria")
                .emailPaciente("email-invalido")
                .cpfPaciente("944.662.490-83")
                .telefonePaciente("11999999999")
                .build();

        Set<ConstraintViolation<Paciente>> violacoes = validator.validate(paciente);
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailPaciente")));
    }

    @Test
    void deveCriarPacienteUsandoMetodoFactory() {
        Paciente paciente = Paciente.createPaciente(
                "Carlos",
                "carlos@email.com",
                "944.662.490-83",
                "11999999999",
                LocalDate.of(1985, 5, 20),
                "Av. Brasil",
                List.of(),
                List.of()
        );

        assertEquals("Carlos", paciente.getNomePaciente());
        assertEquals("Av. Brasil", paciente.getEnderecoPaciente());
        assertNotNull(paciente.getDtNascPaciente());
    }

    @Test
    void deveFalharComNomeInvalido() {
        Paciente paciente = Paciente.builder()
                .nomePaciente("Carlos123") // inválido: contém números
                .emailPaciente("carlos@email.com")
                .cpfPaciente("944.662.490-83")
                .telefonePaciente("11999999999")
                .build();

        Set<ConstraintViolation<Paciente>> violacoes = validator.validate(paciente);
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomePaciente")));
    }

    @Test
    void deveFalharComCpfInvalido() {
        Paciente paciente = Paciente.builder()
                .nomePaciente("Carlos")
                .emailPaciente("carlos@email.com")
                .cpfPaciente("11111111111") // CPF inválido
                .telefonePaciente("11999999999")
                .build();

        Set<ConstraintViolation<Paciente>> violacoes = validator.validate(paciente);
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpfPaciente")));
    }
}

