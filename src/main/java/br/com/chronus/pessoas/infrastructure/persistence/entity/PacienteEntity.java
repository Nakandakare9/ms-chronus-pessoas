package br.com.chronus.pessoas.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PacienteEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true)
   private Integer idPaciente;

   @Column(name = "nome_paciente", nullable = false, length = 100)
   private String nomePaciente;

   @Column(name = "email_paciente", nullable = false, unique = true, length = 100)
   private String emailPaciente;

   @Column(name = "cpf_paciente", nullable = false, unique = true, length = 14)
   private String cpfPaciente;

   @Column(name = "telefone_paciente", nullable = false, length = 15)
   private String telefonePaciente;

   @Column(name = "dt_nasc_paciente", nullable = false)
   private LocalDate dtNascPaciente;

   @Column(name = "endereco_paciente", nullable = false)
   private String enderecoPaciente;

   @ManyToMany
   @JoinTable(
           name = "paciente_contato_anjo",
           joinColumns = @JoinColumn(name = "id_paciente"),
           inverseJoinColumns = @JoinColumn(name = "id_contato_anjo")
   )
   private List<ContatoAnjoEntity> contatoAnjoList;

   @ManyToMany
   @JoinTable(
           name = "paciente_profissional_saude",
           joinColumns = @JoinColumn(name = "id_paciente"),
           inverseJoinColumns = @JoinColumn(name = "id_profissional_saude")
   )
   private List<ProfissionalSaudeEntity> profissionalSaudeList;
}