package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetContatoAnjo {

    private final ContatoAnjoGateway contatoAnjoGateway;

    public ContatoAnjo execute(final UUID idContatoAnjo) {
        return contatoAnjoGateway.getContatoAnjoById(idContatoAnjo)
                .orElseThrow(() -> new ContatoAnjoNaoEncontradoException(idContatoAnjo));
    }
}
