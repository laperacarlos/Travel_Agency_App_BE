package com.kodilla.travelagencybe.exception;

public class WeatherNotFoundException extends Exception {

    public WeatherNotFoundException(String location) {
        super("Current conditions for requested location: " + location + " are not available.");
    }
}
