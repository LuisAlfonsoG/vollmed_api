package med.voll.api.domain.consultations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.doctors.Specialty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationDataRegister(
        @NotNull
        Long patientId,
        Long doctorId,
        @NotNull
        @Future
        LocalDateTime date,
        Specialty specialty) {
}
