package com.kodilla.travelagencybe.exception;
public class ComplaintAnswerNotFoundException extends Exception {

    public ComplaintAnswerNotFoundException(Long id) {
        super("CompliantAnswer with id: " + id + " doesn't exist in database.");
    }
}
