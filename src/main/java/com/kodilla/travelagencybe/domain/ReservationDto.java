package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.enums.TravelType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ReservationDto {
    private final Long id;
    private final User user;
    private final Travel travel;
    private final TravelType travelType;
    private final HotelStandard hotelStandard;
    private final MealStandard mealStandard;
    private final Complaint complaint;
    private final LocalDateTime creationDate;
    private final Status status;
}
