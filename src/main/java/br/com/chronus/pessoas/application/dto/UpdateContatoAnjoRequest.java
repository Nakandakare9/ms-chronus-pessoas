package br.com.chronus.pessoas.application.dto;

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
public class UpdateContatoAnjoRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomeContatoAnjo;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailContatoAnjo;

    @Size(max = 15, message = "Phone number length must be less than 15 characters")
    private String telefoneContatoAnjo;

    @Size(max = 255, message = "Observation length must be less than 255 characters")
    private String observacaoContatoAnjo;

    private List<UUID> idPaciente;

}
