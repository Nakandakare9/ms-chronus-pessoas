package br.com.chronus.pessoas.infrastructure.persistence.repository;

import br.com.chronus.pessoas.infrastructure.persistence.entity.ContatoAnjoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContatoAnjoRepository extends JpaRepository<ContatoAnjoEntity, Integer> {

    Optional<ContatoAnjoEntity> findByNomeContatoAnjo(String nomeContatoAnjo);

    List<ContatoAnjoEntity> findByPacienteList_IdPaciente(Integer idPaciente);
}
