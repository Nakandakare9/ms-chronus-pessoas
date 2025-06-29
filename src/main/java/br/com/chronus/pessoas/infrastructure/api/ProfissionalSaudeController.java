package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("chronus/profissionais-saude")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    @PostMapping
    public ResponseEntity<ProfissionalSaude> createProfissionalSaude(@Validated @RequestBody ProfissionalSaude profissionalSaude) {
        ProfissionalSaude createdProfissionalSaude = profissionalSaudeGateway.createProfissionalSaude(profissionalSaude);
        return new ResponseEntity<>(createdProfissionalSaude, HttpStatus.CREATED);
    }

    @GetMapping("/{idProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> getProfissionalSaudeById(@PathVariable Integer idProfissionalSaude) {
        Optional<ProfissionalSaude> profissionalSaude = profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude);
        return profissionalSaude.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{nomeProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> getProfissionalSaudeById(@PathVariable String nomeProfissionalSaude) {
        Optional<ProfissionalSaude> profissionalSaude = profissionalSaudeGateway.getProfissionalSaudeByNome(nomeProfissionalSaude);
        return profissionalSaude.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{idProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> updateProfissionalSaude(@PathVariable Integer idProfissionalSaude, @Validated @RequestBody ProfissionalSaude profissionalSaude) {
        profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);
        ProfissionalSaude updatedProfissionalSaude = profissionalSaudeGateway.updateProfissionalSaude(profissionalSaude);
        return new ResponseEntity<>(updatedProfissionalSaude, HttpStatus.OK);
    }

    @DeleteMapping("/{idProfissionalSaude}")
    public ResponseEntity<Void> deleteProfissionalSaude(@PathVariable int idProfissionalSaude) {
        profissionalSaudeGateway.deleteProfissionalSaude(idProfissionalSaude);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}


