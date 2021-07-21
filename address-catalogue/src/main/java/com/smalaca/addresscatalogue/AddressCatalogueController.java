package com.smalaca.addresscatalogue;

import com.google.common.collect.ImmutableList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressCatalogueController {
    private static final List<Address> ADDRESSES = ImmutableList.of(
            new Address("Plac Mariacki", "5", "", "Kraków", "Polska"),
            new Address("Plac Jana Matejki", "9", "", "Kraków", "Polska"),
            new Address("Rynek Główny", "43", "2", "Kraków", "Polska")
    );

    @PostMapping("/check")
    public ValidationResult isValid(@RequestBody Address address) {
        if (address.hasAllInformation()) {
            if (ADDRESSES.contains(address)) {
                return new ValidationResult(true, "");
            } else {
                return new ValidationResult(false, "Invalid address");
            }
        } else {
            return new ValidationResult(false, "Not enough information");
        }
    }
}
