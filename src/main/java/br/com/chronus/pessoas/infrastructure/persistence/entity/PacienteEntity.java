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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

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
   private UUID idPaciente;

   @Column(name = "email_paciente", nullable = false, unique = true)
   private String emailPaciente;

   @Column(name = "cpf_paciente", nullable = false, unique = true)
   private String cpfPaciente;

   @Column(name = "telefone_paciente", nullable = false)
   private String telefonePaciente;

   @Column(name = "dt_nasc_paciente", nullable = false)
   private LocalDate dtNascPaciente;

   private String enderecoPaciente;

   @ManyToMany(mappedBy = "pacientes")
   private List<ContatoAnjoEntity> contatosAnjo;
}