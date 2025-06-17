package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeExistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProfissionalSaude {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    public ProfissionalSaude execute(final ProfissionalSaude requestProfissionalSaude) {

        final var profissionalSaude = profissionalSaudeGateway.getProfissionalSaudeById(requestProfissionalSaude.getIdProfissionalSaude());

        if(profissionalSaude.isPresent()) {
            throw new ProfissionalSaudeExistenteException(requestProfissionalSaude.getIdProfissionalSaude(), requestProfissionalSaude.getNomeProfissionalSaude());
        }

        final var buildDomain =
                ProfissionalSaude.createProfissionalSaude(
                        requestProfissionalSaude.getNomeProfissionalSaude(),
                        requestProfissionalSaude.getEmailProfissionalSaude(),
                        requestProfissionalSaude.getEspecialidadeProfissionalSaude(),
                        requestProfissionalSaude.getCrmProfissionalSaude());

        return profissionalSaudeGateway.createProfissionalSaude(buildDomain);
    }
}