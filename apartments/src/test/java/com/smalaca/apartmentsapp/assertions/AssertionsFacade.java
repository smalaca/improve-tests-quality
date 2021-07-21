package com.smalaca.apartmentsapp.assertions;

import com.smalaca.apartmentsapp.apartment.Apartment;
import com.smalaca.apartmentsapp.apartment.ApartmentAssertion;

public class AssertionsFacade {
    public static ApartmentAssertion assertThat(Apartment actual) {
        return new ApartmentAssertion(actual);
    }
}
