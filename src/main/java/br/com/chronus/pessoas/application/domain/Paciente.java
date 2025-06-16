package br.com.chronus.pessoas.application.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {

    private UUID idPaciente;

    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomePaciente;

    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailPaciente;

    @CPF(message = "CPF should be valid")
    private String cpfPaciente;

    @Size(max = 15, message = "Phone number length must be less than 15 characters")
    private String telefonePaciente;

    private LocalDate dtNascPaciente;

    private String enderecoPaciente;

    private List<UUID> idContatoAnjo;

    public static Paciente createPaciente(
            final String nomePaciente,
            final String emailPaciente,
            final String cpfPaciente,
            final String telefonePaciente,
            final LocalDate dtNascPaciente,
            final String enderecoPaciente,
            final List<UUID> idContatoAnjo) {
                return new Paciente(null, nomePaciente, emailPaciente, cpfPaciente, telefonePaciente, dtNascPaciente, enderecoPaciente, idContatoAnjo);
    }

}
