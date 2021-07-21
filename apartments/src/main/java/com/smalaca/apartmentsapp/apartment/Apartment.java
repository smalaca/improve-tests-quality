package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.owner.OwnerId;
import lombok.Getter;

@Getter
public class Apartment {
    private final ApartmentId id = ApartmentId.create();
    private final OwnerId ownerId;
    private final Address address;

    public Apartment(OwnerId ownerId, Address address) {
        this.ownerId = ownerId;
        this.address = address;
    }
}
