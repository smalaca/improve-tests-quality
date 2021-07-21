package com.smalaca.addresscatalogue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
    private boolean isValid;
    private String message;
}
