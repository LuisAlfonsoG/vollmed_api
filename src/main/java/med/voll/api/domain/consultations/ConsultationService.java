package med.voll.api.domain.consultations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultations.Consultation;
import med.voll.api.domain.consultations.ConsultationDataFields;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationsRespository;
import med.voll.api.domain.consultations.validations.ConsultationValidator;
import med.voll.api.domain.doctors.Doctor;
import med.voll.api.domain.doctors.DoctorsRepository;
import med.voll.api.domain.patients.PatientsRepository;
import med.voll.api.infra.errors.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {


    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private ConsultationsRespository consultationsRespository;

    @Autowired
    List<ConsultationValidator> validators;

    public ConsultationDataFields validateConsultationData(ConsultationDataRegister data){
        if(!patientsRepository.existsById(data.patientId()))
            throw new ValidationError("Patient doesn't exists");

        if(data.doctorId() != null && !doctorsRepository.existsById(data.doctorId()))
            throw new ValidationError("Doctor doesn't exists");

        validators.forEach(validator -> validator.validate(data));

        var patient = patientsRepository.getReferenceById(data.patientId());
        var doctor = findNewDoctor(data);

        if (doctor == null)
            throw new ValidationException("There are no doctors available");

        var consultation = consultationsRespository.save(new Consultation(null, patient, doctor, data.date()));
        return new ConsultationDataFields(
                consultation.getId(),
                consultation.getPatient().getName(),
                consultation.getDoctor().getName(),
                consultation.getData()
        );
    }

    public Doctor findNewDoctor(ConsultationDataRegister data){
        if(data.doctorId() != null)
            return doctorsRepository.getReferenceById(data.doctorId());

        if(data.specialty() == null)
            throw new ValidationException("You must provide a doctor or an specialty");

        return doctorsRepository.getAvailableDoctorBySpecialty(data.specialty(), data.date());
    }
}
