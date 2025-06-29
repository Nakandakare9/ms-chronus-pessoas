package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;

import java.util.Optional;

public interface ProfissionalSaudeGateway {

    ProfissionalSaude createProfissionalSaude(final ProfissionalSaude profissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeById(final int idProfissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeByNome(final String nomeProfissionalSaude);
    ProfissionalSaude updateProfissionalSaude(final ProfissionalSaude profissionalSaude);
    void deleteProfissionalSaude(final int idProfissionalSaude);

}
