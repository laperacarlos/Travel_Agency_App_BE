package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.*;
import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ComplaintAnswerMapperTestSuite {

    @Autowired
    ComplaintAnswerMapper complaintAnswerMapper;

    @Autowired
    TimeProvider timeProvider;

    @Test
    void testMapToComplaintAnswer() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        Reservation reservation = new Reservation(2L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        Complaint complaint = new Complaint(1L, reservation, "complaint description",
                timeProvider.getTime(),null, null, Status.OPENED);
        ComplaintAnswerDto complaintAnswerDto = new ComplaintAnswerDto(1L, complaint, "answer",
                LocalDateTime.of(2021, 8, 19, 14, 10));

        //when
        ComplaintAnswer complaintAnswer = complaintAnswerMapper.mapToComplaintAnswer(complaintAnswerDto);

        //then
        assertEquals(1L, complaintAnswer.getId());
        assertEquals(1L, complaintAnswer.getComplaint().getId());
        assertEquals("answer", complaintAnswer.getAnswer());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), complaintAnswer.getCreationDate());
    }

    @Test
    void testMapToComplaintAnswerDto() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        Reservation reservation = new Reservation(2L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        Complaint complaint = new Complaint(1L, reservation, "complaint description",
                timeProvider.getTime(),null, null, Status.OPENED);
        ComplaintAnswer complaintAnswer = new ComplaintAnswer(1L, complaint, "answer",
                LocalDateTime.of(2021, 8, 19, 14, 10));

        //when
        ComplaintAnswerDto complaintAnswerDto = complaintAnswerMapper.mapToComplaintAnswerDto(complaintAnswer);

        //then
        assertEquals(1L, complaintAnswerDto.getId());
        assertEquals(1L, complaintAnswerDto.getComplaint().getId());
        assertEquals("answer", complaintAnswerDto.getAnswer());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), complaintAnswerDto.getCreationDate());
    }
}
