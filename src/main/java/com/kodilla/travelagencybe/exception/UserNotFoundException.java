package com.kodilla.travelagencybe.exception;
public class UserNotFoundException extends Exception {

    public UserNotFoundException(Long id) {
        super("User with id: " + id + " doesn't exist in database.");
    }
}
