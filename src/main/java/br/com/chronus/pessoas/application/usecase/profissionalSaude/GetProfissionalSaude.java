package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProfissionalSaude {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    public ProfissionalSaude execute(final Integer idProfissionalSaude) {
        return profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude)
                .orElseThrow(() -> new ProfissionalSaudeNaoEncontradoException(idProfissionalSaude));
    }
}
