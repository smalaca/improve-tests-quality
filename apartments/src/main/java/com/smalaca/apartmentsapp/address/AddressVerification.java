package com.smalaca.apartmentsapp.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddressVerification {
    private boolean valid;
    private String message;
}
