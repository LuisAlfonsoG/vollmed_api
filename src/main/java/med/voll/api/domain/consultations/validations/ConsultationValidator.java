package med.voll.api.domain.consultations.validations;

import med.voll.api.domain.consultations.ConsultationDataRegister;
import org.springframework.stereotype.Component;

public interface ConsultationValidator {

    void validate(ConsultationDataRegister data);

}
