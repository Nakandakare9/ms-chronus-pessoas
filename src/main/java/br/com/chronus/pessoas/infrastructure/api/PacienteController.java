package br.com.chronus.pessoas.infrastructure.api;

import br.com.chronus.pessoas.application.domain.Paciente;
import br.com.chronus.pessoas.application.gateway.PacienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("chronus/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteGateway pacienteGateway;;

    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@Validated @RequestBody Paciente paciente) {
        Paciente createdPaciente = pacienteGateway.createPaciente(paciente);
        return new ResponseEntity<>(createdPaciente, HttpStatus.CREATED);
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Integer idPaciente) {
        Optional<Paciente> paciente = pacienteGateway.getPacienteById(idPaciente);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{nomePaciente}")
    public ResponseEntity<Paciente> getPacienteByNome(@PathVariable String nomePaciente) {
        Optional<Paciente> paciente = pacienteGateway.getPacienteByNome(nomePaciente);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Integer idPaciente, @Validated @RequestBody Paciente paciente) {
        paciente.setIdPaciente(idPaciente);
        Paciente updatedPaciente = pacienteGateway.updatePaciente(paciente);
        return new ResponseEntity<>(updatedPaciente, HttpStatus.OK);
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Void> deletePaciente(@PathVariable int idPaciente) {
        pacienteGateway.deletePaciente(idPaciente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
