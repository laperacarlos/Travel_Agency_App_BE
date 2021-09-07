package com.kodilla.travelagencybe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime creationDate;
    private final boolean isActive;
    private final boolean isAdministrator;
    private final List<Reservation> listOfReservations;
}
