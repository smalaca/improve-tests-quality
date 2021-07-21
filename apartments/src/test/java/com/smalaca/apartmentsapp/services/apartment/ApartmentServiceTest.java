package com.smalaca.apartmentsapp.services.apartment;

import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.apartment.*;
import com.smalaca.apartmentsapp.events.EventRegistry;
import com.smalaca.apartmentsapp.events.InvalidAddressRecognized;
import com.smalaca.apartmentsapp.events.OwnerNotFound;
import com.smalaca.apartmentsapp.owner.GivenOwner;
import com.smalaca.apartmentsapp.owner.OwnerId;
import com.smalaca.apartmentsapp.owner.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class ApartmentServiceTest {
    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);
    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final AddressCatalogue addressCatalogue = mock(AddressCatalogue.class);
    private final EventRegistry eventRegistry = mock(EventRegistry.class);
    private final ApartmentService service = new ApartmentService(ownerRepository, apartmentRepository, addressCatalogue, eventRegistry);

    private final GivenOwner givenOwner = new GivenOwner(ownerRepository);
    private final GivenApartment givenApartment = new GivenApartment(addressCatalogue, apartmentRepository);

    @Test
    void shouldNotAddApartmentWhenOwnerDoesNotExist() {
        OwnerId ownerId = givenOwner.notExisting();
        ApartmentDto apartmentDto = givenApartment.validDtoForNotExisting();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isEqualTo(ApartmentId.nullObject());
        thenApartmentWasNotCreated();
        ArgumentCaptor<OwnerNotFound> captor = ArgumentCaptor.forClass(OwnerNotFound.class);
        then(eventRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getOwnerId()).isEqualTo(ownerId);
    }

    @Test
    void shouldRecognizeInvalidAddress() {
        OwnerId ownerId = givenOwner.existing();
        ApartmentDto apartmentDto = givenApartment.invalidDto();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isEqualTo(ApartmentId.nullObject());
        thenApartmentWasNotCreated();
        ArgumentCaptor<InvalidAddressRecognized> captor = ArgumentCaptor.forClass(InvalidAddressRecognized.class);
        then(eventRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getStreet()).isEqualTo("Rynek Główny");
        assertThat(captor.getValue().getHouseNumber()).isEqualTo("43");
        assertThat(captor.getValue().getApartmentNumber()).isEqualTo("2");
        assertThat(captor.getValue().getCity()).isEqualTo("Kraków");
        assertThat(captor.getValue().getCountry()).isEqualTo("Polska");
    }

    @Test
    void shouldReturnIdOfExistingApartment() {
        OwnerId ownerId = givenOwner.existing();
        ApartmentDto apartmentDto = givenApartment.dtoForExisting(ownerId);

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        thenApartmentWasNotCreated();
        assertThat(apartmentId)
                .isNotEqualTo(ApartmentId.nullObject())
                .isNotNull();
    }

    private void thenApartmentWasNotCreated() {
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldCreateNewApartment() {
        OwnerId ownerId = givenOwner.existing();
        ApartmentDto apartmentDto = givenApartment.validDtoForNotExisting();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isNotEqualTo(ApartmentId.nullObject());
        ApartmentAssertion.assertThat(thenApartmentCreated())
            .hasIdEqualTo(apartmentId)
            .hasOwnerIdEqualTo(ownerId)
            .hasStreetEqualTo("Rynek Główny")
            .hasHouseNumberEqualTo("43")
            .hasApartmentNumberEqualTo("2")
            .hasCityEqualTo("Kraków")
            .hasCountryEqualTo("Polska");
    }

    private Apartment thenApartmentCreated() {
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        then(apartmentRepository).should().save(captor.capture());
        return captor.getValue();
    }
}