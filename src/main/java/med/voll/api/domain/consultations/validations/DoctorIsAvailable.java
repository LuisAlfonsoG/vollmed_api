package med.voll.api.domain.consultations.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorIsAvailable implements ConsultationValidator{
    @Autowired
    private ConsultationsRespository consultationsRespository;
    public void validate(ConsultationDataRegister data){
        var hasConsult = consultationsRespository.existsByDoctorIdAndData(data.doctorId(), data.date());

        if(hasConsult)
            throw new ValidationException("Doctor is not available");

    }
}
