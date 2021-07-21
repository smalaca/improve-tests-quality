package com.smalaca.apartmentsapp.owner;

import java.util.UUID;

import static org.mockito.BDDMockito.given;

public class GivenOwner {
    private final OwnerRepository ownerRepository;

    public GivenOwner(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerId notExisting() {
        OwnerId ownerId = new OwnerId(UUID.randomUUID());
        given(ownerRepository.exists(ownerId)).willReturn(false);

        return ownerId;
    }

    public OwnerId existing() {
        OwnerId ownerId = new OwnerId(UUID.randomUUID());
        given(ownerRepository.exists(ownerId)).willReturn(true);

        return ownerId;
    }
}
