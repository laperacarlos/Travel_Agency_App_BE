package com.kodilla.travelagencybe.exception;

public class ComplaintNotFoundException extends Exception {

    public ComplaintNotFoundException(Long id) {
        super("Compliant with id: " + id + " doesn't exist in database.");
    }
}
