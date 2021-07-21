package com.smalaca.apartmentsapp.integrationtest.address;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.address.AddressCatalogueFactory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressCatalogueIntegrationTest {
    private final AddressCatalogue addressCatalogue = new AddressCatalogueFactory().addressCatalogue();

    @Test
    void shouldRecognizeMissingFields() {
        Optional<Address> check = addressCatalogue.check("Rynek Główny", null, null, "Kraków", "Polska");
        assertThat(check).isEmpty();
    }

    @Test
    void shouldRecognizeInvalidAddress() {
        Optional<Address> check = addressCatalogue.check("Rynek Główny", "13", "42", "Kraków", "Polska");
        assertThat(check).isEmpty();
    }

    @Test
    void shouldRecognizeValidAddress() {
        Optional<Address> check = addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska");
        assertThat(check).isPresent();
        assertThat(check.get().getStreet()).isEqualTo("Rynek Główny");
        assertThat(check.get().getHouseNumber()).isEqualTo("43");
        assertThat(check.get().getApartmentNumber()).isEqualTo("2");
        assertThat(check.get().getCity()).isEqualTo("Kraków");
        assertThat(check.get().getCountry()).isEqualTo("Polska");
    }
}