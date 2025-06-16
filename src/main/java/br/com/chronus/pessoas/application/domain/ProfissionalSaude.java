package br.com.chronus.pessoas.application.domain;


import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfissionalSaude {

    private UUID idProfissionalSaude;

    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String nomeProfissionalSaude;

    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String emailProfissionalSaude;

    private EnumEspecialidadeProfissionalSaude especialidadeProfissionalSaude;

    private String crmProfissionalSaude;

}
