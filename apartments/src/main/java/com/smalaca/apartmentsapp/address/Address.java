package com.smalaca.apartmentsapp.address;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Address {
    private final String street;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
}
