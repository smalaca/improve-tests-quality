package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.address.Address;

import java.util.Optional;

public interface ApartmentRepository {
    Optional<Apartment> findBy(Address address);

    ApartmentId save(Apartment apartment);
}
