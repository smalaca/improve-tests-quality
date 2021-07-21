package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.integrationtest.address.AddressContract;
import com.smalaca.apartmentsapp.integrationtest.address.AddressContractGiven;
import com.smalaca.apartmentsapp.integrationtest.address.AddressContractScenario;
import com.smalaca.apartmentsapp.owner.OwnerId;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class GivenApartment {
    private final AddressCatalogue addressCatalogue;
    private final ApartmentRepository apartmentRepository;
    private final AddressContract addressContract = new AddressContract();

    public GivenApartment(AddressCatalogue addressCatalogue, ApartmentRepository apartmentRepository) {
        this.addressCatalogue = addressCatalogue;
        this.apartmentRepository = apartmentRepository;
    }

    public ApartmentDto validDtoForNotExisting() {
        AddressContractScenario scenario = addressContract.validAddress();
        givenNotExistingApartment(scenario.expected().get());

        return apartmentDto(givenAddress(scenario));
    }

    private void givenNotExistingApartment(Address address) {
        given(apartmentRepository.findBy(address)).willReturn(Optional.empty());
        given(apartmentRepository.save(any())).will(invocation -> {
            return ((Apartment) invocation.getArgument(0)).getId();
        });
    }

    public ApartmentDto dtoForExisting(OwnerId ownerId) {
        AddressContractScenario scenario = addressContract.validAddress();
        givenExistingApartment(ownerId, scenario.expected().get());

        return apartmentDto(givenAddress(scenario));
    }

    private void givenExistingApartment(OwnerId ownerId, Address address) {
        Apartment apartment = new Apartment(ownerId, address);
        given(apartmentRepository.findBy(address)).willReturn(Optional.of(apartment));
    }

    public ApartmentDto invalidDto() {
        return apartmentDto(givenAddress(addressContract.invalidAddress()));
    }

    private AddressContractGiven givenAddress(AddressContractScenario scenario) {
        AddressContractGiven given = scenario.given();
        given(addressCatalogue.check(given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry()))
                .willReturn(scenario.expected());

        return given;
    }

    private ApartmentDto apartmentDto(AddressContractGiven given) {
        return new ApartmentDto(
                    given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry());
    }
}
