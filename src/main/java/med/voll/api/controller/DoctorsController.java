package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.doctors.*;
import med.voll.api.domain.model.Address;
import med.voll.api.domain.model.AddressData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorsController {

    @Autowired
    private DoctorsRepository doctorsRepository;
    @PostMapping
    public ResponseEntity<UpdateDoctorFields> addNewDoctor(@RequestBody @Valid DoctorsDataRegister doctorData,
                                                           UriComponentsBuilder uriComponentsBuilder){
        Doctor doctor = doctorsRepository.save(new Doctor(doctorData));
        Address address = doctor.getAddress();
        URI url = uriComponentsBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
        UpdateDoctorFields doctorFields = new UpdateDoctorFields(
                doctor.getId(),
                doctor.getName(),
                doctor.getDocument(),
                new AddressData(
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getZipcode()
                ));
        return ResponseEntity.created(url).body(doctorFields);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListRepresentation>> getDoctorsList(@PageableDefault(size = 10, sort = "name") Pageable pageable){
        return ResponseEntity.ok(
                doctorsRepository.findByActiveTrue(pageable)
                        .map(DoctorListRepresentation::new)
        );
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateDoctorData(@RequestBody @Valid UpdateDoctorFields updateDoctorFields){
        Doctor doctor = doctorsRepository.getReferenceById(updateDoctorFields.id());
        doctor.updateData(updateDoctorFields);
        Address address = doctor.getAddress();
        return ResponseEntity.ok(new UpdateDoctorFields(
                doctor.getId(),
                doctor.getName(),
                doctor.getDocument(),
                new AddressData(
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getZipcode()
                )));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDoctor(@PathVariable Long id){
        Doctor doc = doctorsRepository.getReferenceById(id);
        doc.desactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateDoctorFields> getDoctor(@PathVariable Long id){
        Doctor doctor = doctorsRepository.getReferenceById(id);

        Address address = doctor.getAddress();
        return ResponseEntity.ok(new UpdateDoctorFields(
                doctor.getId(),
                doctor.getName(),
                doctor.getDocument(),
                new AddressData(
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getZipcode()
                )));
    }
}
