package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteProfissionalSaude {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    public void execute(final UUID idProfissionalSaude) {
        final var profissionalSaude = profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude)
                .orElseThrow(() -> new ProfissionalSaudeNaoEncontradoException(idProfissionalSaude));

        profissionalSaudeGateway.deleteProfissionalSaude(profissionalSaude.getIdProfissionalSaude());
    }
}
