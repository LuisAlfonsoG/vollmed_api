package med.voll.api.domain.doctors;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.AddressData;

public record UpdateDoctorFields(@NotNull Long id, String name, String document, AddressData address) {
}
