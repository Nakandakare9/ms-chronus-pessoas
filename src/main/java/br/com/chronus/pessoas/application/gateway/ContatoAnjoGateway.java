package br.com.chronus.pessoas.application.gateway;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContatoAnjoGateway {

    ContatoAnjo createContatoAnjo(final ContatoAnjo contatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoById(final int idContatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoByNome(final String nomeContatoAnjo);
    ContatoAnjo updateContatoAnjo(final ContatoAnjo contatoAnjo);
    void deleteContatoAnjo(final int idContatoAnjo);
    Optional<ContatoAnjo> getContatoAnjoByPacienteId(final int idPaciente);
    List<ContatoAnjo> findAllContatoAnjo();

}
