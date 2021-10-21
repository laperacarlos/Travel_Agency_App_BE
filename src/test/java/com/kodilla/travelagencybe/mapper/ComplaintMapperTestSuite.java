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
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ComplaintMapperTestSuite {

    @Autowired
    ComplaintMapper complaintMapper;

    @Autowired
    TimeProvider timeProvider;

    @Test
    void testMapToComplaint() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        Reservation reservation = new Reservation(2L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        ComplaintDto complaintDto = new ComplaintDto(1L, reservation, "complaint description",
                LocalDateTime.of(2021, 8, 19, 14, 10), null, null, Status.OPENED);

        //when
        Complaint complaint = complaintMapper.mapToComplaint(complaintDto);

        //then
        assertEquals(1L, complaint.getId());
        assertEquals(2L, complaint.getReservation().getId());
        assertEquals("complaint description", complaint.getDescription());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), complaint.getCreationDate());
        assertNull(complaint.getClosingDate());
        assertNull(complaint.getComplaintAnswer());
        assertEquals(Status.OPENED, complaint.getStatus());
    }

    @Test
    void testMapToComplaintDto() {
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
                LocalDateTime.of(2021, 8, 19, 14, 10), null, null, Status.OPENED);

        //when
        ComplaintDto complaintDto = complaintMapper.mapToComplaintDto(complaint);

        //then
        assertEquals(1L, complaintDto.getId());
        assertEquals(2L, complaintDto.getReservation().getId());
        assertEquals("complaint description", complaintDto.getDescription());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), complaintDto.getCreationDate());
        assertNull(complaintDto.getClosingDate());
        assertNull(complaintDto.getComplaintAnswer());
        assertEquals(Status.OPENED, complaintDto.getStatus());
    }
}
