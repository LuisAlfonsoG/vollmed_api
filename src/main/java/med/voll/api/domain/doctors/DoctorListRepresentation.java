package med.voll.api.domain.doctors;

public record DoctorListRepresentation(Long id,String name, String email, String document, String specialty) {

    public DoctorListRepresentation(Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getDocument(), doctor.getSpecialty().toString());
    }
}
