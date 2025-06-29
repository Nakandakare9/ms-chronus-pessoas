package br.com.chronus.pessoas.application.domain;

import br.com.chronus.pessoas.application.enums.EnumParentesco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContatoAnjo {

    private Integer idContatoAnjo;

    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomeContatoAnjo;

    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailContatoAnjo;

    @CPF(message = "CPF should be valid")
    private String cpfContatoAnjo;

    @Size(max = 15, message = "Phone number length must be less than 15 characters")
    private String telefoneContatoAnjo;

    private EnumParentesco parentescoContatoAnjo;

    @Size(max = 255, message = "Observation length must be less than 255 characters")
    private String observacaoContatoAnjo;

    private List<Paciente> pacienteList;

    public static ContatoAnjo createContatoAnjo(
            final String nomeContatoAnjo,
            final String emailContatoAnjo,
            final String cpfContatoAnjo,
            final String telefoneContatoAnjo,
            final EnumParentesco parentescoContatoAnjo,
            final String observacaoContatoAnjo,
            final List<Paciente> pacienteList) {
        return new ContatoAnjo(null, nomeContatoAnjo, emailContatoAnjo, cpfContatoAnjo, telefoneContatoAnjo, parentescoContatoAnjo, observacaoContatoAnjo, pacienteList);
    }

}
