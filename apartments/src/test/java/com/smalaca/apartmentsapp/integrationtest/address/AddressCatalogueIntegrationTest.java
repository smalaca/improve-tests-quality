package com.smalaca.apartmentsapp.integrationtest.address;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.address.AddressCatalogueFactory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressCatalogueIntegrationTest {
    private final AddressCatalogue addressCatalogue = new AddressCatalogueFactory().addressCatalogue();
    private final AddressContract addressContract = new AddressContract();

    @Test
    void shouldRecognizeMissingFields() {
        AddressContractScenario scenario = addressContract.missingFields();
        AddressContractGiven given = scenario.given();
        
        Optional<Address> check = addressCatalogue.check(given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry());
        
        assertThat(check).isEqualTo(scenario.expected());
    }

    @Test
    void shouldRecognizeInvalidAddress() {
        AddressContractScenario scenario = addressContract.invalidAddress();
        AddressContractGiven given = scenario.given();

        Optional<Address> check = addressCatalogue.check(given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry());

        assertThat(check).isEqualTo(scenario.expected());
    }

    @Test
    void shouldRecognizeValidAddress() {
        AddressContractScenario scenario = addressContract.validAddress();
        AddressContractGiven given = scenario.given();

        Optional<Address> check = addressCatalogue.check(given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry());

        assertThat(check).isEqualTo(scenario.expected());
    }
}