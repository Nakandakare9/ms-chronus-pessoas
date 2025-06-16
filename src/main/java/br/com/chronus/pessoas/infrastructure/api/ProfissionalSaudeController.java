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
    public ResponseEntity<ProfissionalSaude> getProfissionalSaudeById(@PathVariable UUID idProfissionalSaude) {
        Optional<ProfissionalSaude> profissionalSaude = profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude);
        return profissionalSaude.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{idProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> updateProfissionalSaude(@PathVariable UUID idProfissionalSaude, @Validated @RequestBody ProfissionalSaude profissionalSaude) {
        profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);
        ProfissionalSaude updatedProfissionalSaude = profissionalSaudeGateway.updateProfissionalSaude(idProfissionalSaude, profissionalSaude);
        return new ResponseEntity<>(updatedProfissionalSaude, HttpStatus.OK);
    }

    @DeleteMapping("/{idProfissionalSaude}")
    public ResponseEntity<Void> deleteProfissionalSaude(@PathVariable UUID idProfissionalSaude) {
        boolean deleted = profissionalSaudeGateway.deleteProfissionalSaude(idProfissionalSaude);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
