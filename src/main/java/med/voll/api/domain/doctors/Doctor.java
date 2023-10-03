package med.voll.api.domain.doctors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.model.Address;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String phone_number;
    String document;
    @Enumerated(EnumType.STRING)
    Specialty specialty;
    @Embedded
    Address address;
    Boolean active;

    public Doctor(DoctorsDataRegister doctorData) {
        this.active = true;
        this.name = doctorData.name();
        this.email = doctorData.email();
        this.phone_number = doctorData.phone_number();
        this.document = doctorData.document();
        this.specialty = doctorData.specialty();
        this.address = new Address(doctorData.address());
    }

    public void updateData(UpdateDoctorFields updateDoctorFields) {
        if (updateDoctorFields.name() != null)
            this.name = updateDoctorFields.name();
        if (updateDoctorFields.document() != null)
            this.document = updateDoctorFields.document();
        if (updateDoctorFields.address() != null)
            this.address = this.address.updateData(updateDoctorFields.address());
    }

    public void desactivate(){
        this.active = false;
    }
}
