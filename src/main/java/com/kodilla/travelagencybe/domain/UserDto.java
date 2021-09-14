package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.UserStatus;
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
    private final UserStatus isActive;
    private final UserStatus isAdministrator;
    private final List<Reservation> listOfReservations;
}
