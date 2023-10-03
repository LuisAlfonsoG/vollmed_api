package med.voll.api.domain.consultations.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class OpeningHours implements ConsultationValidator {

    public void validate(ConsultationDataRegister data){
        var isSunday = DayOfWeek.SUNDAY.equals(data.date().getDayOfWeek());
        var isBeforeOpen = data.date().getHour() < 7;
        var isAfterClose = data.date().getHour() > 19;

        if(isSunday || isBeforeOpen || isAfterClose)
            throw new ValidationException("Clinic open hours are from Monday to Sunday, 7 AM to 7 PM");

    }
}
