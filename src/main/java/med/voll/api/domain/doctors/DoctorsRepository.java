package med.voll.api.domain.doctors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findByActiveTrue(Pageable pageable);

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.specialty =:specialty
            AND d.active = true
            AND d.id not in (
                SELECT c.doctor.id FROM Consultation c
                WHERE c.data =:date
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Doctor getAvailableDoctorBySpecialty(Specialty specialty, LocalDateTime date);
}
