package com.kodilla.travelagencybe.exception;

public class TravelSkyNotFoundException extends Exception {

    public TravelSkyNotFoundException(Long id) {
        super("TravelSky with id: " + id + " doesn't exist in database.");
    }
}
