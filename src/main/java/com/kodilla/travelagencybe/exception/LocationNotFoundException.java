package com.kodilla.travelagencybe.exception;
public class LocationNotFoundException extends Exception {

    public LocationNotFoundException(String location) {
        super("Requested location: " + location + " doesn't exist in weather database.");
    }
}
