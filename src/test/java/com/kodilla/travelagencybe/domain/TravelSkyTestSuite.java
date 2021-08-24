package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.exception.TravelSkyNotFoundException;
import com.kodilla.travelagencybe.repository.TravelSkyDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TravelSkyTestSuite {

    @Autowired
    TravelSkyDao travelSkyDao;

    @Test
    public void testCreateTravel() {
        //given
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                LocalDateTime.of(2021, 8, 19, 14, 10));

        //when
        TravelSky savedTravelSky = travelSkyDao.save(travelSky);

        //then
        assertNotEquals(0L, savedTravelSky.getId());

        //clean
        travelSkyDao.deleteAll();
    }

    @Test
    public void testReadTravelSky() throws Exception {
        //given
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelSkyDao.save(travelSky);

        //when
        TravelSky travelSkyFromDb = travelSkyDao.findById(travelSky.getId()).orElseThrow(() -> new TravelSkyNotFoundException(travelSky.getId()));

        //then
        assertEquals("Warsaw", travelSkyFromDb.getOrigin());
        assertEquals("Boston", travelSkyFromDb.getDestination());
        assertEquals(LocalDateTime.of(2021, 11, 7, 21, 10), travelSkyFromDb.getDepartureDate());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), travelSkyFromDb.getReturnDate());
        assertEquals(HotelStandard.FOUR, travelSkyFromDb.getHotelStandard());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), travelSkyFromDb.getCreationDate());

        //clean
        travelSkyDao.deleteAll();
    }

    @Test
    public void testUpdateTravelSky() {
        //given
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelSkyDao.save(travelSky);

        //when
        travelSky.setOrigin("Wroclaw");
        travelSky.setDestination("Paris");
        travelSky.setHotelStandard(HotelStandard.ONE);
        TravelSky updatedTravelSky = travelSkyDao.save(travelSky);

        //then
        assertEquals("Wroclaw", updatedTravelSky.getOrigin());
        assertEquals("Paris", updatedTravelSky.getDestination());
        assertEquals(HotelStandard.ONE, updatedTravelSky.getHotelStandard());

        //clean
        travelSkyDao.deleteAll();
    }

    @Test
    public void testDeleteTravelSky() {
        //given
        //given
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                LocalDateTime.of(2021, 8, 19, 14, 10));
        travelSkyDao.save(travelSky);

        //when
        travelSkyDao.deleteById(travelSky.getId());
        Optional<TravelSky> optTravelSky = travelSkyDao.findById(travelSky.getId());

        //then
        assertFalse(optTravelSky.isPresent());
    }
}
