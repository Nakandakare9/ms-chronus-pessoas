package br.com.chronus.pessoas.infrastructure.persistence.entity;

import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "profissional_saude")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProfissionalSaudeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idProfissionalSaude;

    @Column(name = "nome_profissional_saude", length = 100, nullable = false)
    private String nomeProfissionalSaude;

    @Column(name = "email_profissional_saude", length = 100)
    private String emailProfissionalSaude;

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidade_profissional_saude")
    private EnumEspecialidadeProfissionalSaude especialidadeProfissionalSaude;

    @Column(name = "crm_profissional_saude", nullable = false, unique = true, length = 6)
    private String crmProfissionalSaude;

    @ManyToMany(mappedBy = "profissionalSaudeList")
    private List<PacienteEntity> pacienteList;
}