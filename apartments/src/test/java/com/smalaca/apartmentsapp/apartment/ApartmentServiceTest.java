package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.owner.OwnerId;
import org.junit.jupiter.api.Test;

class ApartmentServiceTest {
    private final ApartmentService service = null;

    @Test
    void shouldReturnIdOfExistingApartment() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenDtoForExistingApartment();

        ApartmentId actual = service.add(ownerId, apartmentDto);

        thenApartmentShouldNotBeCreated(actual);
        thenShouldReturnIdOfExistingApartment(actual);
    }

    private ApartmentDto givenDtoForExistingApartment() {
        return null;
    }

    private void thenShouldReturnIdOfExistingApartment(ApartmentId actual) {

    }

    @Test
    void shouldCreateApartment() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenValidApartmentDto();

        ApartmentId actual = service.add(ownerId, apartmentDto);

        thenApartmentShouldBeCreated(actual, apartmentDto);
    }

    private void thenApartmentShouldBeCreated(ApartmentId actual, ApartmentDto apartmentDto) {

    }

    @Test
    void shouldRecognizeAddressIsInvalid() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenInvalidApartmentDto();

        ApartmentId actual = service.add(ownerId, apartmentDto);

        thenApartmentShouldNotBeCreated(actual);
        thenInvalidAddressRecognized(apartmentDto);
    }

    private OwnerId givenExistingOwner() {
        return null;
    }

    private ApartmentDto givenInvalidApartmentDto() {
        return null;
    }

    private void thenInvalidAddressRecognized(ApartmentDto apartmentDto) {

    }

    @Test
    void shouldRecognizeOwnerDoesNotExist() {
        OwnerId ownerId = givenNotExistingOwner();
        ApartmentDto apartmentDto = givenValidApartmentDto();

        ApartmentId actual = service.add(ownerId, apartmentDto);

        thenApartmentShouldNotBeCreated(actual);
        thenOwnerNotFoundRecognized(ownerId);
    }

    private OwnerId givenNotExistingOwner() {
        return null;
    }

    private ApartmentDto givenValidApartmentDto() {
        return null;
    }

    private void thenApartmentShouldNotBeCreated(ApartmentId actual) {

    }

    private void thenOwnerNotFoundRecognized(OwnerId ownerId) {

    }
}