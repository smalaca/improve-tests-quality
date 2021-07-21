package com.smalaca.apartmentsapp.integrationtest.address;

import com.smalaca.apartmentsapp.address.AddressTestFactory;

import java.util.Optional;

import static com.smalaca.apartmentsapp.address.AddressTestFactory.*;

public class AddressContract {
    public static final String INVALID_HOUSE_NUMBER = "13";
    public static final String INVALID_APARTMENT_NUMBER = "42";

    public AddressContractScenario missingFields() {
        return new AddressContractScenario(new AddressContractGiven(STREET, null, null, CITY, COUNTRY), Optional.empty());
    }

    public AddressContractScenario invalidAddress() {
        return new AddressContractScenario(
                new AddressContractGiven(STREET, INVALID_HOUSE_NUMBER, INVALID_APARTMENT_NUMBER, CITY, COUNTRY), Optional.empty());
    }

    public AddressContractScenario validAddress() {
        return new AddressContractScenario(
                new AddressContractGiven(STREET, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY),
                Optional.of(AddressTestFactory.create()));
    }
}
