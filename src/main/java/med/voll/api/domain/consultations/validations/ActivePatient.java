package med.voll.api.domain.consultations.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.patients.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatient implements ConsultationValidator {

    @Autowired
    private PatientsRepository patientsRepository;
    public void validate(ConsultationDataRegister data){
        if (data.patientId() == null)
            return;

        var active = patientsRepository.findActiveById(data.patientId());

        if (!active)
            throw new ValidationException("Inactive patient");

    }
}
