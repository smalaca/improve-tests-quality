package com.smalaca.apartmentsapp.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidAddressRecognized {
    private final String street;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
}
