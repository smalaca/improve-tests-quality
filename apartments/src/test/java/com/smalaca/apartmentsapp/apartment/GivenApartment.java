package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.address.AddressTestFactory;
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

    public GivenApartment(AddressCatalogue addressCatalogue, ApartmentRepository apartmentRepository) {
        this.addressCatalogue = addressCatalogue;
        this.apartmentRepository = apartmentRepository;
    }

    public ApartmentDto validDtoForNotExisting() {
        ApartmentDto apartmentDto = new ApartmentDto("Rynek Główny", "43", "2", "Kraków", "Polska");
        Address address = AddressTestFactory.create();
        given(addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska")).willReturn(Optional.of(address));
        given(apartmentRepository.findBy(address)).willReturn(Optional.empty());
        given(apartmentRepository.save(any())).will(invocation -> {
            return ((Apartment) invocation.getArgument(0)).getId();
        });
        return apartmentDto;
    }

    public ApartmentDto dtoForExisting(OwnerId ownerId) {
        ApartmentDto apartmentDto = new ApartmentDto("Rynek Główny", "43", "2", "Kraków", "Polska");
        Address address = AddressTestFactory.create();
        given(addressCatalogue.check("Rynek Główny", "43", "2", "Kraków", "Polska")).willReturn(Optional.of(address));
        Apartment apartment = new Apartment(ownerId, address);
        given(apartmentRepository.findBy(address)).willReturn(Optional.of(apartment));
        return apartmentDto;
    }

    public ApartmentDto invalidDto() {
        AddressContractScenario scenario = new AddressContract().invalidAddress();
        AddressContractGiven given = scenario.given();
        ApartmentDto apartmentDto = new ApartmentDto(
                given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry());

        given(addressCatalogue.check(given.getStreet(), given.getHouseNumber(), given.getApartmentNumber(), given.getCity(), given.getCountry()))
                .willReturn(scenario.expected());

        return apartmentDto;
    }
}
