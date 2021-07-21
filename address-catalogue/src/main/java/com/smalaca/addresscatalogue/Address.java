package com.smalaca.addresscatalogue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String country;

    boolean hasAllInformation() {
        return street != null && houseNumber != null && apartmentNumber != null && city != null && country != null;
    }
}
