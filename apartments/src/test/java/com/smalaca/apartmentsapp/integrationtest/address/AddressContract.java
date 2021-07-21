package com.smalaca.apartmentsapp.integrationtest.address;

import com.smalaca.apartmentsapp.address.AddressTestFactory;

import java.util.Optional;

public class AddressContract {
    public AddressContractScenario missingFields() {
        return new AddressContractScenario(new AddressContractGiven("Rynek Główny", null, null, "Kraków", "Polska"), Optional.empty());
    }

    public AddressContractScenario invalidAddress() {
        return new AddressContractScenario(new AddressContractGiven("Rynek Główny", "13", "42", "Kraków", "Polska"), Optional.empty());
    }

    public AddressContractScenario validAddress() {
        return new AddressContractScenario(new AddressContractGiven("Rynek Główny", "43", "2", "Kraków", "Polska"), Optional.of(AddressTestFactory.create()));
    }
}
