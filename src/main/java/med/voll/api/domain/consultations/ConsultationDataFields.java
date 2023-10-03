package med.voll.api.domain.consultations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationDataFields(Long id, String patientName, String doctorName, LocalDateTime date) {
}
