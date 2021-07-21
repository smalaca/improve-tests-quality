package com.smalaca.apartmentsapp.address;

import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class AddressCatalogue {
    private final RestTemplate restTemplate;
    private final String url;

    AddressCatalogue(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Optional<Address> check(String street, String houseNumber, String apartmentNumber, String city, String country) {
        Address address = new Address(street, houseNumber, apartmentNumber, city, country);
        AddressVerification verification = restTemplate.postForObject(url + "/address/check", address, AddressVerification.class);

        if (verification.isValid()) {
            return Optional.of(address);
        } else {
            return Optional.empty();
        }
    }
}
