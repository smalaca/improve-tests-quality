package com.smalaca.apartmentsapp.address;

public class AddressTestFactory {
    public static final String STREET = "Rynek Główny";
    public static final String HOUSE_NUMBER = "43";
    public static final String APARTMENT_NUMBER = "2";
    public static final String CITY = "Kraków";
    public static final String COUNTRY = "Polska";

    public static Address create() {
        return new Address(STREET, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY);
    }
}