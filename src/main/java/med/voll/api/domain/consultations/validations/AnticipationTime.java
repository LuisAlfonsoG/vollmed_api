package med.voll.api.domain.consultations.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.infra.errors.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AnticipationTime implements ConsultationValidator{

    public void validate(ConsultationDataRegister data){
        var now = LocalDateTime.now();
        var is30minInterval = Duration.between(now, data.date()).toMinutes() < 30;
        if (is30minInterval) {
            throw new ValidationException("Consultations must be made with at least 30 minutes of anticipation");
        }

    }

}
