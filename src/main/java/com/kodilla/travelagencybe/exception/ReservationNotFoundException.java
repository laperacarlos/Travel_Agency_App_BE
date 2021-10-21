package com.kodilla.travelagencybe.exception;

public class ReservationNotFoundException extends Exception {

    public ReservationNotFoundException(Long id) {
        super("Reservation with id: " + id + " doesn't exist in database.");
    }
}
