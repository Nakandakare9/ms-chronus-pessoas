package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalSaudeGateway {

    ProfissionalSaude createProfissionalSaude(final ProfissionalSaude profissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeById(final UUID idProfissionalSaude);
    ProfissionalSaude updateProfissionalSaude(UUID idProfissionalSaude, ProfissionalSaude profissionalSaude);
    boolean deleteProfissionalSaude(UUID idProfissionalSaude);

}
