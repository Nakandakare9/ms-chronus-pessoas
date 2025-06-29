package br.com.chronus.pessoas.application.usecase.profissionalSaude;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.dto.UpdateProfissionalSaudeRequest;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import br.com.chronus.pessoas.application.usecase.profissionalSaude.exception.ProfissionalSaudeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProfissionalSaude {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    public ProfissionalSaude execute(final Integer idProfissionalSaude, final UpdateProfissionalSaudeRequest updateProfissionalSaudeRequest) {
        final var profissionalSaudeEncontrado = profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude)
                .orElseThrow(() -> new ProfissionalSaudeNaoEncontradoException(idProfissionalSaude));

        profissionalSaudeEncontrado.setNomeProfissionalSaude(updateProfissionalSaudeRequest.getNomeProfissionalSaude());
        profissionalSaudeEncontrado.setEmailProfissionalSaude(updateProfissionalSaudeRequest.getEmailProfissionalSaude());
        profissionalSaudeEncontrado.setEspecialidadeProfissionalSaude(updateProfissionalSaudeRequest.getEspecialidadeProfissionalSaude());

        return profissionalSaudeGateway.updateProfissionalSaude(profissionalSaudeEncontrado);
    }
}
