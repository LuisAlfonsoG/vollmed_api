package med.voll.api.domain.consultations.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientDoesNotHaveConsult implements ConsultationValidator{

    @Autowired
    private ConsultationsRespository consultationsRespository;
    public void validate(ConsultationDataRegister data){
        var firstValidHour = data.date().withHour(7);
        var lastValidHour = data.date().withHour(16);

        var isValid = consultationsRespository.existsByPatientIdAndDataBetween(data.patientId(), firstValidHour, lastValidHour);
        if(isValid)
            throw new ValidationException("Patient has already scheduled a consultation");
    }
}
