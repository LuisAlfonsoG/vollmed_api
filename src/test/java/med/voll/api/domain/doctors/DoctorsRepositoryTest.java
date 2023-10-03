package med.voll.api.domain.doctors;

import med.voll.api.domain.consultations.Consultation;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationsRespository;
import med.voll.api.domain.model.AddressData;
import med.voll.api.domain.patients.Patient;
import med.voll.api.domain.patients.PatientDataRegister;
import med.voll.api.domain.patients.PatientsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorsRepositoryTest {

    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private ConsultationsRespository consultationsRespository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Returns null if Doctor is not available at given date time")
    void getDoctorBySpecialtyCase1(){
        var testDateTime = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var patient = createPatient("Ferras", "f.s@gamil.com", "141454");
        var new_doctor = createDoctor("Anuel", "sacacita@not.com", "161616", Specialty.CARDIOLOGY);

        em.persist(new Consultation(null, patient, new_doctor, testDateTime));
        var doctor = doctorsRepository.getAvailableDoctorBySpecialty(Specialty.CARDIOLOGY, testDateTime);

        assertThat(doctor).isNull();
    }

    @Test
    @DisplayName("Returns Doctor if doctor is available")
    void getDoctorBySpecialtyCase2(){
        var testDateTime = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var new_doctor = createDoctor("Anuel", "sacacita@not.com", "161616", Specialty.NEUROLOGY);

        var doctor = doctorsRepository.getAvailableDoctorBySpecialty(Specialty.NEUROLOGY, testDateTime);

        assertThat(doctor).isEqualTo(new_doctor);
    }
    private Doctor createDoctor(String name, String email, String document, Specialty specialty){
        var doc = new Doctor(
                new DoctorsDataRegister(name, email, "55555555",document, specialty,
                        new AddressData("1", "city", "state", "1234"))
        );
        em.persist(doc);
        return doc;
    }

    private Patient createPatient(String name, String email, String document){
        var pat = new Patient(new PatientDataRegister(name, email, document,
                        new AddressData("2", "city", "Chango", "1313")));
        em.persist(pat);
        return pat;
    }
}