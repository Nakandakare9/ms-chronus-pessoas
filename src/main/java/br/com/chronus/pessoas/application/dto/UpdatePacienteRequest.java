package br.com.chronus.pessoas.application.dto;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdatePacienteRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomePaciente;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailPaciente;

    @Size(max = 15, message = "Phone number length must be less than 15 characters")
    private String telefonePaciente;

    private String enderecoPaciente;

    private List<ContatoAnjo> contatoAnjoList;


}
