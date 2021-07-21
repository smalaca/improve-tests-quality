package com.smalaca.apartmentsapp.services.apartment;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.apartment.*;
import com.smalaca.apartmentsapp.events.EventRegistry;
import com.smalaca.apartmentsapp.events.InvalidAddressRecognized;
import com.smalaca.apartmentsapp.events.OwnerNotFound;
import com.smalaca.apartmentsapp.owner.OwnerId;
import com.smalaca.apartmentsapp.owner.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class ApartmentServiceTest {
    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);
    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final AddressCatalogue addressCatalogue = mock(AddressCatalogue.class);
    private final EventRegistry eventRegistry = mock(EventRegistry.class);
    private final ApartmentService service = new ApartmentService(ownerRepository, apartmentRepository, addressCatalogue, eventRegistry);

    @Test
    void shouldNotAddApartmentWhenOwnerDoesNotExist() {
        OwnerId ownerId = givenNotExistingOwner();
        ApartmentDto apartmentDto = givenValidApartmentDto();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isEqualTo(ApartmentId.nullObject());
        then(apartmentRepository).should(never()).save(any());
        ArgumentCaptor<OwnerNotFound> captor = ArgumentCaptor.forClass(OwnerNotFound.class);
        then(eventRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getOwnerId()).isEqualTo(ownerId);
    }

    private OwnerId givenNotExistingOwner() {
        OwnerId ownerId = new OwnerId(UUID.randomUUID());
        given(ownerRepository.exists(ownerId)).willReturn(false);

        return ownerId;
    }

    @Test
    void shouldRecognizeInvalidAddress() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenInvalidApartmentDto();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isEqualTo(ApartmentId.nullObject());
        then(apartmentRepository).should(never()).save(any());
        ArgumentCaptor<InvalidAddressRecognized> captor = ArgumentCaptor.forClass(InvalidAddressRecognized.class);
        then(eventRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getStreet()).isEqualTo("Rynek Główny");
        assertThat(captor.getValue().getHouseNumber()).isEqualTo("43");
        assertThat(captor.getValue().getApartmentNumber()).isEqualTo("2");
        assertThat(captor.getValue().getCity()).isEqualTo("Kraków");
        assertThat(captor.getValue().getCountry()).isEqualTo("Polska");
    }

    private ApartmentDto givenInvalidApartmentDto() {
        ApartmentDto apartmentDto = new ApartmentDto("Rynek Główny", "43", "2", "Kraków", "Polska");
        given(addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska")).willReturn(Optional.empty());
        return apartmentDto;
    }

    @Test
    void shouldReturnIdOfExistingApartment() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenDtoForExistingApartment(ownerId);

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isNotEqualTo(ApartmentId.nullObject());
        assertThat(apartmentId).isNotNull();
        then(apartmentRepository).should(never()).save(any());
    }

    private ApartmentDto givenDtoForExistingApartment(OwnerId ownerId) {
        ApartmentDto apartmentDto = new ApartmentDto("Rynek Główny", "43", "2", "Kraków", "Polska");
        Address address = new Address("Rynek Główny", "43", "2", "Kraków", "Polska");
        given(addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska")).willReturn(Optional.of(address));
        Apartment apartment = new Apartment(ownerId, address);
        given(apartmentRepository.findBy(address)).willReturn(Optional.of(apartment));
        return apartmentDto;
    }

    @Test
    void shouldCreateNewApartment() {
        OwnerId ownerId = givenExistingOwner();
        ApartmentDto apartmentDto = givenValidApartmentDto();

        ApartmentId apartmentId = service.add(ownerId, apartmentDto);

        assertThat(apartmentId).isNotEqualTo(ApartmentId.nullObject());
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        then(apartmentRepository).should().save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(apartmentId);
        assertThat(captor.getValue().getOwnerId()).isEqualTo(ownerId);
        assertThat(captor.getValue().getAddress().getStreet()).isEqualTo("Rynek Główny");
        assertThat(captor.getValue().getAddress().getHouseNumber()).isEqualTo("43");
        assertThat(captor.getValue().getAddress().getApartmentNumber()).isEqualTo("2");
        assertThat(captor.getValue().getAddress().getCity()).isEqualTo("Kraków");
        assertThat(captor.getValue().getAddress().getCountry()).isEqualTo("Polska");
    }

    private ApartmentDto givenValidApartmentDto() {
        ApartmentDto apartmentDto = new ApartmentDto("Rynek Główny", "43", "2", "Kraków", "Polska");
        Address address = new Address("Rynek Główny", "43", "2", "Kraków", "Polska");
        given(addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska")).willReturn(Optional.of(address));
        given(apartmentRepository.findBy(address)).willReturn(Optional.empty());
        given(apartmentRepository.save(any())).will(invocation -> {
            return ((Apartment) invocation.getArgument(0)).getId();
        });
        return apartmentDto;
    }

    private OwnerId givenExistingOwner() {
        OwnerId ownerId = new OwnerId(UUID.randomUUID());
        given(ownerRepository.exists(ownerId)).willReturn(true);
        return ownerId;
    }
}