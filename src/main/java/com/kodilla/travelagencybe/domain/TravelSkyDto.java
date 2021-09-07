package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class TravelSkyDto {
    private final Long id;
    private final String origin;
    private final String destination;
    private final LocalDateTime departureDate;
    private final LocalDateTime returnDate;
    private final Status status;
    private final LocalDateTime creationDate;
    private final List<Reservation> listOfReservations;
}
