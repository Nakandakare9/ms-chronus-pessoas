package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ContatoAnjo;
import br.com.chronus.pessoas.application.gateway.ContatoAnjoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/id/{idContatoAnjo}")
    public ResponseEntity<ContatoAnjo> getContatoAnjoById(@PathVariable Integer idContatoAnjo) {
        Optional<ContatoAnjo> contatoAnjo = contatoAnjoGateway.getContatoAnjoById(idContatoAnjo);
        return contatoAnjo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nome/{nomeContatoAnjo}")
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
    public ResponseEntity<Void> deleteContatoAnjo(@PathVariable int idContatoAnjo) {
        contatoAnjoGateway.deleteContatoAnjo(idContatoAnjo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<ContatoAnjo>> getContatoAnjoByPacienteId(@PathVariable int idPaciente) {
        List<ContatoAnjo> contatoAnjos = contatoAnjoGateway.getContatoAnjoByPacienteId(idPaciente);
        if (contatoAnjos == null || contatoAnjos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contatoAnjos, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ContatoAnjo>> findAllContatoAnjo() {
        List<ContatoAnjo> contatoAnjos = contatoAnjoGateway.findAllContatoAnjo();
        if (contatoAnjos == null || contatoAnjos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contatoAnjos, HttpStatus.OK);
    }
}