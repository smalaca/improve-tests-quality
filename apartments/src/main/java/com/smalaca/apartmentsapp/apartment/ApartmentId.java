package com.smalaca.apartmentsapp.apartment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
public class ApartmentId {
    private static final ApartmentId NULL_OBJECT = new ApartmentId(null);
    private final UUID id;

    public static ApartmentId nullObject() {
        return NULL_OBJECT;
    }

    public static ApartmentId create() {
        return new ApartmentId(UUID.randomUUID());
    }
}
