package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.ProfissionalSaude;
import br.com.chronus.pessoas.application.enums.EnumEspecialidadeProfissionalSaude;
import br.com.chronus.pessoas.application.gateway.ProfissionalSaudeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chronus/profissionais-saude")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeGateway profissionalSaudeGateway;

    @PostMapping
    public ResponseEntity<ProfissionalSaude> createProfissionalSaude(@Validated @RequestBody ProfissionalSaude profissionalSaude) {
        ProfissionalSaude created = profissionalSaudeGateway.createProfissionalSaude(profissionalSaude);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/id/{idProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> getById(@PathVariable Integer idProfissionalSaude) {
        return profissionalSaudeGateway.getProfissionalSaudeById(idProfissionalSaude)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nome/{nomeProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> getByNome(@PathVariable String nomeProfissionalSaude) {
        return profissionalSaudeGateway.getProfissionalSaudeByNome(nomeProfissionalSaude)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/crm/{crmProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> getByCrm(@PathVariable String crmProfissionalSaude) {
        return profissionalSaudeGateway.getProfissionalSaudeByCrm(crmProfissionalSaude)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<ProfissionalSaude>> getByEspecialidade(@PathVariable EnumEspecialidadeProfissionalSaude especialidade) {
        List<ProfissionalSaude> lista = profissionalSaudeGateway.findByEspecialidade(especialidade);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> getAll() {
        List<ProfissionalSaude> lista = profissionalSaudeGateway.findAllProfissionalSaude();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PutMapping("/{idProfissionalSaude}")
    public ResponseEntity<ProfissionalSaude> update(@PathVariable Integer idProfissionalSaude, @Validated @RequestBody ProfissionalSaude profissionalSaude) {
        profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);
        ProfissionalSaude updated = profissionalSaudeGateway.updateProfissionalSaude(profissionalSaude);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{idProfissionalSaude}")
    public ResponseEntity<Void> delete(@PathVariable int idProfissionalSaude) {
        profissionalSaudeGateway.deleteProfissionalSaude(idProfissionalSaude);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}