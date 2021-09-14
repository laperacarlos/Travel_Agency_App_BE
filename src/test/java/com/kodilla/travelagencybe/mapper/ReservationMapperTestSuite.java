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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationMapperTestSuite {

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    TimeProvider timeProvider;

    @Test
    void mapToReservation() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        ReservationDto reservationDto = new ReservationDto(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2021, 8, 19, 14, 10), Status.OPENED);

        //when
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);

        //then
        assertEquals(1L, reservation.getId());
        assertEquals(2L, reservation.getUser().getId());
        assertEquals(2L, reservation.getTravel().getId());
        assertEquals(TravelType.BASIC, reservation.getTravelType());
        assertEquals(HotelStandard.FOUR, reservation.getHotelStandard());
        assertEquals(MealStandard.ALL_INCLUSIVE, reservation.getMealStandard());
        assertNull(reservation.getComplaint());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), reservation.getCreationDate());
        assertEquals(Status.OPENED, reservation.getStatus());
    }

    @Test
    void mapToReservationDto() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        Reservation reservation = new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2021, 8, 19, 14, 10), Status.OPENED);

        //when
        ReservationDto reservationDto = reservationMapper.mapToReservationDto(reservation);

        //then
        assertEquals(1L, reservationDto.getId());
        assertEquals(2L, reservationDto.getUser().getId());
        assertEquals(2L, reservationDto.getTravel().getId());
        assertEquals(TravelType.BASIC, reservationDto.getTravelType());
        assertEquals(HotelStandard.FOUR, reservationDto.getHotelStandard());
        assertEquals(MealStandard.ALL_INCLUSIVE, reservationDto.getMealStandard());
        assertNull(reservationDto.getComplaint());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), reservationDto.getCreationDate());
        assertEquals(Status.OPENED, reservationDto.getStatus());
    }

    @Test
    void mapToReservationDtoList() {
        //given
        User user = new User(2L, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(2L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        List<Reservation> reservationList = List.of(new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2021, 8, 19, 14, 10), Status.OPENED));

        //when
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservationList);

        //then
        assertEquals(1, reservationDtoList.size());
    }
}
