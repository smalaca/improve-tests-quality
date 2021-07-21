package com.smalaca.apartmentsapp.address;

import org.springframework.web.client.RestTemplate;

public class AddressCatalogueFactory {
    public AddressCatalogue addressCatalogue() {
        return new AddressCatalogue(new RestTemplate(), "http://localhost:8103/");
    }
}
