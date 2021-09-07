package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ComplaintDto {
    private final Long id;
    private final Reservation reservation;
    private final String description;
    private final LocalDateTime creationDate;
    private final LocalDateTime closingDate;
    private final ComplaintAnswer complaintAnswer;
    private final Status status;
}
