package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.patients.Patient;
import med.voll.api.domain.patients.PatientDataFields;
import med.voll.api.domain.patients.PatientDataRegister;
import med.voll.api.domain.patients.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
public class PatientsController {

    @Autowired
    private PatientsRepository patientsRepository;
    @PostMapping
    @Transactional
    public ResponseEntity addPatient(@RequestBody @Valid PatientDataRegister data, UriComponentsBuilder uriComponentsBuilder){
        Patient patient = patientsRepository.save(new Patient(data));
        URI url = uriComponentsBuilder.path("/doctors/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(url).body(new PatientDataFields(patient.getId(), patient.getName(), patient.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDataFields> getPatient(@PathVariable Long id){
        Patient patient = patientsRepository.getReferenceById(id);
        PatientDataFields patientFound = new PatientDataFields(patient.getId(), patient.getName(), patient.getEmail());

        return ResponseEntity.ok(patientFound);
    }

}
