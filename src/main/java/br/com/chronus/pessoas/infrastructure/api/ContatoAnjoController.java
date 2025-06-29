package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("chronus/contatos-anjo")
@RequiredArgsConstructor
public class ContatoAnjoController {

    private final ContatoAnjoGateway contatoAnjoGateway;

    @PostMapping
    public ResponseEntity<ContatoAnjo> createContatoAnjo(@Validated @RequestBody ContatoAnjo contatoAnjo) {
        ContatoAnjo createdContatoAnjo = contatoAnjoGateway.createContatoAnjo(contatoAnjo);
        return new ResponseEntity<>(createdContatoAnjo, HttpStatus.CREATED);
    }

    @GetMapping("/{idContatoAnjo}")
    public ResponseEntity<ContatoAnjo> getContatoAnjoById(@PathVariable Integer idContatoAnjo) {
        Optional<ContatoAnjo> contatoAnjo = contatoAnjoGateway.getContatoAnjoById(idContatoAnjo);
        return contatoAnjo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{nomeContatoAnjo}")
    public ResponseEntity<ContatoAnjo> getContatoAnjoByNome(@PathVariable String nomeContatoAnjo) {
        Optional<ContatoAnjo> contatoAnjo = contatoAnjoGateway.getContatoAnjoByNome(nomeContatoAnjo);
        return contatoAnjo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{idContatoAnjo}")
    public ResponseEntity<ContatoAnjo> updateContatoAnjo(@PathVariable Integer idContatoAnjo, @Validated @RequestBody ContatoAnjo contatoAnjo) {
        contatoAnjo.setIdContatoAnjo(idContatoAnjo);
        ContatoAnjo updatedContatoAnjo = contatoAnjoGateway.updateContatoAnjo(contatoAnjo);
        return new ResponseEntity<>(updatedContatoAnjo, HttpStatus.OK);
    }

    @DeleteMapping("/{idContatoAnjo}")
    public ResponseEntity<Void> deleteContatoAnjo(@PathVariable Integer idContatoAnjo) {
        boolean deleted = contatoAnjoGateway.deleteContatoAnjo(idContatoAnjo);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<ContatoAnjo> getContatoAnjoByPacienteId(@PathVariable Integer idPaciente) {
        Optional<ContatoAnjo> contatoAnjo = contatoAnjoGateway.getContatoAnjoByPacienteId(idPaciente);
        return contatoAnjo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findAll")
    public ResponseEntity<ContatoAnjo> findAllContatoAnjo() {
        Optional<ContatoAnjo> contatoAnjo = contatoAnjoGateway.findAllContatoAnjo();
        return contatoAnjo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
