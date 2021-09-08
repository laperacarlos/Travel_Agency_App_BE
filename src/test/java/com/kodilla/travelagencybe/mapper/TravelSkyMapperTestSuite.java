package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.TravelSky;
import com.kodilla.travelagencybe.domain.TravelSkyDto;
import com.kodilla.travelagencybe.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TravelSkyMapperTestSuite {

    @Autowired
    TravelSkyMapper travelSkyMapper;

    @Test
    void testMapToTravelSky() {
        //given
        TravelSkyDto travelSkyDto = new TravelSkyDto(1L, "Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        //when
        TravelSky travelSky = travelSkyMapper.mapToTravelSky(travelSkyDto);

        //then
        assertEquals(1L, travelSky.getId());
        assertEquals("Warsaw", travelSky.getOrigin());
        assertEquals("Boston", travelSky.getDestination());
        assertEquals(LocalDateTime.of(2021, 11, 7, 21, 10), travelSky.getDepartureDate());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), travelSky.getReturnDate());
        assertEquals(Status.OPENED, travelSky.getStatus());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), travelSky.getCreationDate());
        assertEquals(0, travelSky.getListOfReservations().size());
    }

    @Test
    void testMapToTravelSkyDto() {
        //given
        TravelSky travelSky = new TravelSky(1L, "Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        //when
        TravelSkyDto travelSkyDto = travelSkyMapper.mapToTravelSkyDto(travelSky);

        //then
        assertEquals(1L, travelSkyDto.getId());
        assertEquals("Warsaw", travelSkyDto.getOrigin());
        assertEquals("Boston", travelSkyDto.getDestination());
        assertEquals(LocalDateTime.of(2021, 11, 7, 21, 10), travelSkyDto.getDepartureDate());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), travelSkyDto.getReturnDate());
        assertEquals(Status.OPENED, travelSkyDto.getStatus());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), travelSkyDto.getCreationDate());
        assertEquals(0, travelSkyDto.getListOfReservations().size());
    }

    @Test
    void testMapToTravelDtoList() {
        //given
        List<TravelSky> travelList = List.of(new TravelSky(1L, "Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>()));

        //when
        List<TravelSkyDto> travelSkyDtoList = travelSkyMapper.mapToTravelSkyDtoList(travelList);

        //then
        assertEquals(1, travelSkyDtoList.size());
    }
}
