package med.voll.api.domain.model;

import jakarta.validation.constraints.NotBlank;

public record AddressData (
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String zipcode
){ }
