package med.voll.api.domain.consultations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultationsRespository extends JpaRepository<Consultation, Long> {
    Boolean existsByPatientIdAndDataBetween(Long patiendId, LocalDateTime firstValidHour, LocalDateTime lastValidHour);

    Boolean existsByDoctorIdAndData(Long doctorId, LocalDateTime date);
}
