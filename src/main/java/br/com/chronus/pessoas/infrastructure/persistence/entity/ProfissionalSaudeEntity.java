package br.com.chronus.pessoas.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import jakarta.persistence.Column;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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
    private UUID idProfissionalSaude;

    @Column(name = "nome_profissional_saude", length = 100, nullable = false)
    private String nomeProfissionalSaude;

    @Column(name = "email_profissional_saude", length = 100)
    private String emailProfissionalSaude;

    @Column(name = "especialidade_profissional_saude")
    private String especialidadeProfissionalSaude;

    @Column(name = "crm_profissional_saude")
    private String crmProfissionalSaude;
}