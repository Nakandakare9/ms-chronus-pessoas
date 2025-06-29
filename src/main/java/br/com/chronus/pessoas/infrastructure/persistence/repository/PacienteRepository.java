package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.infrastructure.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Integer> {

    Optional<PacienteEntity> findByNomePaciente(String nomePaciente);

}
