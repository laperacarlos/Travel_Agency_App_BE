package com.kodilla.travelagencybe.exception;

public class TravelNotFoundException extends Exception {

    public TravelNotFoundException(Long id) {
        super("Travel with id: " + id + " doesn't exist in database.");
    }
}
