package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;

import java.util.List;
import java.util.Optional;

public interface ProfissionalSaudeGateway {

    ProfissionalSaude createProfissionalSaude(final ProfissionalSaude profissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeById(final int idProfissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeByNome(final String nomeProfissionalSaude);
    Optional<ProfissionalSaude> getProfissionalSaudeByCrm(final String crmProfissionalSaude);
    List<ProfissionalSaude> findByEspecialidade(final EnumEspecialidadeProfissionalSaude especialidade);
    List<ProfissionalSaude> findAllProfissionalSaude();
    ProfissionalSaude updateProfissionalSaude(final ProfissionalSaude profissionalSaude);
    void deleteProfissionalSaude(final int idProfissionalSaude);

}