package com.smalaca.apartmentsapp.apartment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class ApartmentDto {
    private final String street;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
}
