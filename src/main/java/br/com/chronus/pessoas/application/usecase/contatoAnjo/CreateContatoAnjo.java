package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoExistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateContatoAnjo {

    private final ContatoAnjoGateway contatoAnjoGateway;

    public ContatoAnjo execute(final ContatoAnjo requestContatoAnjo) {

        final var contatoAnjo = contatoAnjoGateway.getContatoAnjoById(requestContatoAnjo.getIdContatoAnjo());

        if (contatoAnjo.isPresent()) {
            throw new ContatoAnjoExistenteException(requestContatoAnjo.getIdContatoAnjo(), requestContatoAnjo.getNomeContatoAnjo());
        }

        final var buildDomain = ContatoAnjo.createContatoAnjo(
                requestContatoAnjo.getNomeContatoAnjo(),
                requestContatoAnjo.getEmailContatoAnjo(),
                requestContatoAnjo.getCpfContatoAnjo(),
                requestContatoAnjo.getTelefoneContatoAnjo(),
                requestContatoAnjo.getParentescoContatoAnjo(),
                requestContatoAnjo.getObservacaoContatoAnjo(),
                requestContatoAnjo.getIdPaciente()
        );

        return contatoAnjoGateway.createContatoAnjo(buildDomain);
    }
}
