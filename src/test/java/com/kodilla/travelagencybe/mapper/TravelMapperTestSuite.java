package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.TravelDto;
import com.kodilla.travelagencybe.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TravelMapperTestSuite {

    @Autowired
    TravelMapper travelMapper;

    @Test
    void testMapToTravel() {
        //given
        TravelDto travelDto = new TravelDto(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        //when
        Travel travel = travelMapper.mapToTravel(travelDto);

        //then
        assertEquals(1L, travel.getId());
        assertEquals("Warsaw", travel.getOrigin());
        assertEquals("Boston", travel.getDestination());
        assertEquals(LocalDate.of(2021, 11, 7), travel.getDepartureDate());
        assertEquals(LocalDate.of(2021, 11, 27), travel.getReturnDate());
        assertEquals(Status.OPENED, travel.getStatus());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), travel.getCreationDate());
        assertEquals(0, travel.getListOfReservations().size());
    }

    @Test
    void testMapToTravelDto() {
        //given
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        //when
        TravelDto travelDto = travelMapper.mapToTravelDto(travel);

        //then
        assertEquals(1L, travelDto.getId());
        assertEquals("Warsaw", travelDto.getOrigin());
        assertEquals("Boston", travelDto.getDestination());
        assertEquals(LocalDate.of(2021, 11, 7), travelDto.getDepartureDate());
        assertEquals(LocalDate.of(2021, 11, 27), travelDto.getReturnDate());
        assertEquals(Status.OPENED, travelDto.getStatus());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), travelDto.getCreationDate());
        assertEquals(0, travelDto.getListOfReservations().size());
    }

    @Test
    void testMapToTravelDtoList() {
        //given
        List<Travel> travelList = List.of(new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>()));

        //when
        List<TravelDto> travelDtoList = travelMapper.mapToTravelDtoList(travelList);

        //then
        assertEquals(1, travelDtoList.size());
    }
}
