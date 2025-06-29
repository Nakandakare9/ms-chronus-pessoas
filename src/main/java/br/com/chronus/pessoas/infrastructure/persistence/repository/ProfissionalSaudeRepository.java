package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.infrastructure.persistence.entity.ProfissionalSaudeEntity;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaudeEntity, Integer> {

    Optional<ProfissionalSaudeEntity> findByNomeProfissionalSaude(String nomeProfissionalSaude);

    Optional<ProfissionalSaudeEntity> findByCrmProfissionalSaude(String crmProfissionalSaude);

    List<ProfissionalSaudeEntity> findByEspecialidadeProfissionalSaude(EnumEspecialidadeProfissionalSaude especialidadeProfissionalSaude);

}