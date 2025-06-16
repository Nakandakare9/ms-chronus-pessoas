package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteContatoAnjo {

    private final ContatoAnjoGateway contatoAnjoGateway;

    public void execute(final UUID idContatoAnjo) {
        final var contatoAnjo = contatoAnjoGateway.getContatoAnjoById(idContatoAnjo)
                .orElseThrow(() -> new ContatoAnjoNaoEncontradoException(idContatoAnjo));

        contatoAnjoGateway.deleteContatoAnjo(contatoAnjo.getIdContatoAnjo());
    }
}
