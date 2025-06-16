package br.com.chronus.pessoas.application.usecase.contatoAnjo;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.dto.UpdateContatoAnjoRequest;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import br.com.chronus.pessoas.application.usecase.contatoAnjo.exception.ContatoAnjoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateContatoAnjo {

    private final ContatoAnjoGateway contatoAnjoGateway;

    public ContatoAnjo execute(final UUID idContatoAnjo, final UpdateContatoAnjoRequest updateContatoAnjoRequest) {
        final var contatoAnjoEncontrado = contatoAnjoGateway.getContatoAnjoByPacienteId(idContatoAnjo)
                .orElseThrow(() -> new ContatoAnjoNaoEncontradoException(idContatoAnjo));

        contatoAnjoEncontrado.setNomeContatoAnjo(updateContatoAnjoRequest.getNomeContatoAnjo());
        contatoAnjoEncontrado.setEmailContatoAnjo(updateContatoAnjoRequest.getEmailContatoAnjo());
        contatoAnjoEncontrado.setTelefoneContatoAnjo(updateContatoAnjoRequest.getTelefoneContatoAnjo());
        contatoAnjoEncontrado.setObservacaoContatoAnjo(updateContatoAnjoRequest.getObservacaoContatoAnjo());
        contatoAnjoEncontrado.setIdPaciente(updateContatoAnjoRequest.getIdPaciente());

        return contatoAnjoGateway.updateContatoAnjo(contatoAnjoEncontrado);

    }
}
