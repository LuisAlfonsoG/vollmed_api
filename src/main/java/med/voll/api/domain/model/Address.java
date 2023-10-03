package med.voll.api.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {
    String street;
    String city;
    String state;
    String zipcode;

    public Address(AddressData address) {
        this.street = address.street();
        this.city = address.city();
        this.state = address.state();
        this.zipcode = address.zipcode();
    }

    public Address updateData(AddressData address) {
        this.street = address.street();
        this.city = address.city();
        this.state = address.state();
        this.zipcode = address.zipcode();

        return this;
    }
}
