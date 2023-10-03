package med.voll.api.domain.patients;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.model.Address;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String document;
    @Embedded
    private Address address;
    private Boolean active;

    public Patient(PatientDataRegister data) {
        this.name = data.name();
        this.email = data.email();
        this.document = data.document();
        this.address = new Address(data.address());
        this.active = true;
    }
}
