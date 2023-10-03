package med.voll.api.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientsRepository extends JpaRepository<Patient, Long> {
    @Query("""
            SELECT p.active FROM Patient p
            WHERE p.id =:patientId
            """)
    Boolean findActiveById(Long patientId);
}
