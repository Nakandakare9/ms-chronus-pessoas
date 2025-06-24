package br.com.chronus.pessoas.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contato_anjo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ContatoAnjoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private UUID idContatoAnjo;

    @Column(name = "nome_contato_anjo", length = 100, nullable = false)
    private String nomeContatoAnjo;

    @Column(name = "email_contato_anjo", length = 100)
    private String emailContatoAnjo;

    @Column(name = "cpf_contato_anjo", length = 14, unique = true)
    private String cpfContatoAnjo;

    @Column(name = "telefone_contato_anjo", length = 15)
    private String telefoneContatoAnjo;

    @Column(name = "parentesco_contato_anjo")
    private String parentescoContatoAnjo;

    @Column(name = "observacao_contato_anjo", length = 255)
    private String observacaoContatoAnjo;

    @ManyToMany
    @JoinTable(
            name = "contato_anjo_paciente",
            joinColumns = @JoinColumn(name = "id_contato_anjo"),
            inverseJoinColumns = @JoinColumn(name = "id_paciente")
    )
    private List<PacienteEntity> pacientes;
}