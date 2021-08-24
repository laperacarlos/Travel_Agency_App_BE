package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.TravelType;
import com.kodilla.travelagencybe.exception.TravelNotFoundException;
import com.kodilla.travelagencybe.repository.TravelDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TravelTestSuite {

    @Autowired
    TravelDao travelDao;

    @Test
    public void testCreateTravel() {
        //given
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                LocalDateTime.of(2021, 8, 19, 14, 10));

        //when
        Travel savedTravel = travelDao.save(travel);

        //then
        assertNotEquals(0L, savedTravel.getId());

        //clean
        travelDao.deleteAll();
    }

    @Test
    public void testReadTravel() throws Exception {
        //given
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelDao.save(travel);

        //when
        Travel travelFromDb = travelDao.findById(travel.getId()).orElseThrow(() -> new TravelNotFoundException(travel.getId()));

        //then
        assertEquals("Warsaw", travelFromDb.getOrigin());
        assertEquals("Boston", travelFromDb.getDestination());
        assertEquals(LocalDateTime.of(2021, 11, 7, 21, 10), travelFromDb.getDepartureDate());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), travelFromDb.getReturnDate());
        assertEquals(TravelType.BASIC, travelFromDb.getTravelType());
        assertEquals(HotelStandard.FOUR, travelFromDb.getHotelStandard());
        assertEquals(MealStandard.ALL_INCLUSIVE_ALC_FREE, travelFromDb.getMealStandard());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), travelFromDb.getCreationDate());

        //clean
        travelDao.deleteAll();
    }

    @Test
    public void testUpdateTravel() {
        //given
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelDao.save(travel);

        //when
        travel.setOrigin("Wroclaw");
        travel.setDestination("Paris");
        travel.setTravelType(TravelType.SIGHTSEEING);
        travel.setHotelStandard(HotelStandard.ONE);
        travel.setMealStandard(MealStandard.ALL_INCLUSIVE);
        Travel updatedTravel = travelDao.save(travel);

        //then
        assertEquals("Wroclaw", updatedTravel.getOrigin());
        assertEquals("Paris", updatedTravel.getDestination());
        assertEquals(TravelType.SIGHTSEEING, updatedTravel.getTravelType());
        assertEquals(HotelStandard.ONE, updatedTravel.getHotelStandard());
        assertEquals(MealStandard.ALL_INCLUSIVE, updatedTravel.getMealStandard());

        //clean
        travelDao.deleteAll();
    }

    @Test
    public void testDeleteTravel() {
        //given
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelDao.save(travel);

        //when
        travelDao.deleteById(travel.getId());
        Optional<Travel> optTravel = travelDao.findById(travel.getId());

        //then
        assertFalse(optTravel.isPresent());
    }
}
