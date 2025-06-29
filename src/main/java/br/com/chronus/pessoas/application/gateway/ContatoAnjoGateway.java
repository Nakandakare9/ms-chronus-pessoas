package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;

import java.util.Optional;
import java.util.UUID;

public interface ContatoAnjoGateway {

    ContatoAnjo createContatoAnjo(final ContatoAnjo contatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoById(final int idContatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoByNome(final String nomeContatoAnjo);
    ContatoAnjo updateContatoAnjo(final ContatoAnjo contatoAnjo);
    boolean deleteContatoAnjo(final int idContatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoByPacienteId(final Integer idPaciente);
    Optional<ContatoAnjo> findAllContatoAnjo();

}
