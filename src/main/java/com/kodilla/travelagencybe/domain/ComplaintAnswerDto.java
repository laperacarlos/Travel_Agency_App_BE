package com.kodilla.travelagencybe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ComplaintAnswerDto {
    private final Long id;
    private final Complaint complaint;
    private final String answer;
    private final LocalDateTime creationDate;
}
