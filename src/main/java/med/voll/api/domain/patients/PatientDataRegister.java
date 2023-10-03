package med.voll.api.domain.patients;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.model.AddressData;

public record PatientDataRegister(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String document,

        @Valid
        AddressData address
) {
}
