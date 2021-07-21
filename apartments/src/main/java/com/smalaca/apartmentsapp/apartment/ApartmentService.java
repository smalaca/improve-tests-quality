package com.smalaca.apartmentsapp.apartment;

import com.smalaca.apartmentsapp.address.Address;
import com.smalaca.apartmentsapp.address.AddressCatalogue;
import com.smalaca.apartmentsapp.events.ApartmentAlreadyCreated;
import com.smalaca.apartmentsapp.events.EventRegistry;
import com.smalaca.apartmentsapp.events.InvalidAddressRecognized;
import com.smalaca.apartmentsapp.events.OwnerNotFound;
import com.smalaca.apartmentsapp.owner.OwnerId;
import com.smalaca.apartmentsapp.owner.OwnerRepository;

import java.util.Optional;

public class ApartmentService {
    private static final ApartmentId NO_ID = ApartmentId.nullObject();

    private final OwnerRepository ownerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AddressCatalogue addressCatalogue;
    private final EventRegistry eventRegistry;

    public ApartmentService(
            OwnerRepository ownerRepository, ApartmentRepository apartmentRepository,
            AddressCatalogue addressCatalogue, EventRegistry eventRegistry) {
        this.ownerRepository = ownerRepository;
        this.apartmentRepository = apartmentRepository;
        this.addressCatalogue = addressCatalogue;
        this.eventRegistry = eventRegistry;
    }

    public ApartmentId add(OwnerId ownerId, ApartmentDto dto) {
        if (ownerRepository.exists(ownerId)) {
            Optional<Address> validated = addressCatalogue.check(dto.getStreet(), dto.getHouseNumber(), dto.getApartmentNumber(), dto.getCity(), dto.getCountry());

            if (validated.isPresent()) {
                Address address = validated.get();

                Optional<Apartment> found = apartmentRepository.findBy(address);

                if (found.isPresent()) {
                    Apartment apartment = found.get();

                    eventRegistry.publish(new ApartmentAlreadyCreated(apartment.getId(), dto.getStreet(), dto.getHouseNumber(), dto.getApartmentNumber(), dto.getCity(), dto.getCountry()));
                    return apartment.getId();
                } else {
                    Apartment apartment = new Apartment(ownerId, address);
                    return apartmentRepository.save(apartment);
                }

            } else {
                eventRegistry.publish(new InvalidAddressRecognized(dto.getStreet(), dto.getHouseNumber(), dto.getApartmentNumber(), dto.getCity(), dto.getCountry()));
            }
        } else {
            eventRegistry.publish(new OwnerNotFound(ownerId));
        }

        return NO_ID;
    }
}
