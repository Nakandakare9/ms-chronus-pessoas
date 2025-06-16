package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;

import java.util.Optional;
import java.util.UUID;

public interface ContatoAnjoGateway {

    ContatoAnjo createContatoAnjo(final ContatoAnjo contatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoById(final UUID idContatoAnjo);
    ContatoAnjo updateContatoAnjo(final ContatoAnjo contatoAnjo);
    boolean deleteContatoAnjo(final UUID idContatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoByPacienteId(final UUID idPaciente);
    Optional<ContatoAnjo> findAllContatoAnjo();

}
