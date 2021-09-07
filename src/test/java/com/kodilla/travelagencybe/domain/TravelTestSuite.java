package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.exception.TravelNotFoundException;
import com.kodilla.travelagencybe.repository.TravelDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TravelTestSuite {

    @Autowired
    TravelDao travelDao;

    @Autowired
    TimeProvider timeProvider;

    @Test
    public void testCreateTravel() {
        //given
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());

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
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED,  LocalDateTime.of(2021, 8, 19, 14, 10), new ArrayList<>());
        travelDao.save(travel);

        //when
        Travel travelFromDb = travelDao.findById(travel.getId()).orElseThrow(() -> new TravelNotFoundException(travel.getId()));

        //then
        assertEquals("Warsaw", travelFromDb.getOrigin());
        assertEquals("Boston", travelFromDb.getDestination());
        assertEquals(LocalDate.of(2021, 11, 7), travelFromDb.getDepartureDate());
        assertEquals(LocalDate.of(2021, 11, 27), travelFromDb.getReturnDate());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), travelFromDb.getCreationDate());

        //clean
        travelDao.deleteAll();
    }

    @Test
    public void testUpdateTravel() {
        //given
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);

        //when
        travel.setOrigin("Wroclaw");
        travel.setDestination("Paris");
        Travel updatedTravel = travelDao.save(travel);

        //then
        assertEquals("Wroclaw", updatedTravel.getOrigin());
        assertEquals("Paris", updatedTravel.getDestination());

        //clean
        travelDao.deleteAll();
    }

    @Test
    public void testDeleteTravel() {
        //given
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);

        //when
        travelDao.deleteById(travel.getId());
        Optional<Travel> optTravel = travelDao.findById(travel.getId());

        //then
        assertFalse(optTravel.isPresent());
    }
}
