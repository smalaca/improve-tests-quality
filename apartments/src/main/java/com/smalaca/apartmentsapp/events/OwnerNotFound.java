package com.smalaca.apartmentsapp.events;

import com.smalaca.apartmentsapp.owner.OwnerId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerNotFound {
    private final OwnerId ownerId;
}
