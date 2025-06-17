package br.com.chronus.pessoas.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfissionalSaudeRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomeProfissionalSaude;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailProfissionalSaude;

    private String especialidadeProfissionalSaude;

}
