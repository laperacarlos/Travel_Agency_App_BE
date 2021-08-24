package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.enums.TravelType;
import com.kodilla.travelagencybe.exception.ReservationNotFoundException;
import com.kodilla.travelagencybe.exception.TravelNotFoundException;
import com.kodilla.travelagencybe.exception.TravelSkyNotFoundException;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.repository.ReservationDao;
import com.kodilla.travelagencybe.repository.TravelDao;
import com.kodilla.travelagencybe.repository.TravelSkyDao;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationTestSuite {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TravelDao travelDao;

    @Autowired
    TravelSkyDao travelSkyDao;

    @Autowired
    TimeProvider timeProvider;

    @AfterEach
    public void databaseCleaning() {
        reservationDao.deleteAll();
        travelSkyDao.deleteAll();
        travelDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testCreateReservation() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                timeProvider.getTime());
        travelSkyDao.save(travelSky);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        Reservation skyReservation = new Reservation(user, travelSky, timeProvider.getTime());

        //when
        Reservation savedReservation = reservationDao.save(reservation);
        Reservation savedSkyReservation = reservationDao.save(skyReservation);

        //then
        assertNotEquals(0L, savedReservation.getId());
        assertNotEquals(0L, savedSkyReservation.getId());
    }

    @Test
    public void testReadReservation() throws Exception {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                timeProvider.getTime());
        travelSkyDao.save(travelSky);
        Reservation travelReservation = new Reservation(user, travel, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(travelReservation);
        Reservation skyReservation = new Reservation(user, travelSky, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(skyReservation);

        //when
        Reservation travelReservationFromDb = reservationDao.findById(travelReservation.getId()).orElseThrow(() -> new ReservationNotFoundException(travelReservation.getId()));
        Reservation skyReservationFromDb = reservationDao.findById(skyReservation.getId()).orElseThrow(() -> new ReservationNotFoundException(skyReservation.getId()));

        //then
        assertEquals(user.getId(), travelReservationFromDb.getUser().getId());
        assertEquals(travel.getId(), travelReservationFromDb.getTravel().getId());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), travelReservationFromDb.getCreationDate());
        assertEquals(Status.OPENED, travelReservationFromDb.getStatus());

        assertEquals(user.getId(), skyReservationFromDb.getUser().getId());
        assertEquals(travelSky.getId(), skyReservationFromDb.getTravelSky().getId());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), skyReservationFromDb.getCreationDate());
        assertEquals(Status.OPENED, skyReservationFromDb.getStatus());
    }

    @Test
    public void testUpdateReservation() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                timeProvider.getTime());
        travelSkyDao.save(travelSky);
        Reservation travelReservation = new Reservation(user, travel, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(travelReservation);
        Reservation skyReservation = new Reservation(user, travelSky, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(skyReservation);

        //when
        travelReservation.setStatus(Status.CANCELED);
        travelReservation.setCreationDate(LocalDateTime.of(2021, 8, 22, 15, 10));
        Reservation updatedTravelReservation = reservationDao.save(travelReservation);
        skyReservation.setStatus(Status.CLOSED);
        skyReservation.setCreationDate(LocalDateTime.of(2021, 9, 22, 14, 10));
        Reservation updatedSkyReservation = reservationDao.save(skyReservation);

        //then
        assertEquals(Status.CANCELED, updatedTravelReservation.getStatus());
        assertEquals(LocalDateTime.of(2021, 8, 22, 15, 10), updatedTravelReservation.getCreationDate());

        assertEquals(Status.CLOSED, updatedSkyReservation.getStatus());
        assertEquals(LocalDateTime.of(2021, 9, 22, 14, 10), updatedSkyReservation.getCreationDate());
    }

    @Test
    public void testDeleteReservation() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                timeProvider.getTime());
        travelSkyDao.save(travelSky);
        Reservation travelReservation = new Reservation(user, travel, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(travelReservation);
        Reservation skyReservation = new Reservation(user, travelSky, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(skyReservation);

        //when
        reservationDao.deleteById(travelReservation.getId());
        reservationDao.deleteById(skyReservation.getId());
        Optional<Reservation> optTravelReservation = reservationDao.findById(travelReservation.getId());
        Optional<Reservation> optSkyReservation = reservationDao.findById(skyReservation.getId());

        //then
        assertFalse(optTravelReservation.isPresent());
        assertFalse(optSkyReservation.isPresent());
    }

    @Test
    public void testRelationsWithEntities() throws Exception {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        TravelSky travelSky = new TravelSky("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                HotelStandard.FOUR,
                timeProvider.getTime());
        travelSkyDao.save(travelSky);
        Reservation travelReservation = new Reservation(user, travel, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(travelReservation);
        Reservation skyReservation = new Reservation(user, travelSky, LocalDateTime.of(2021, 8, 19, 14, 10));
        reservationDao.save(skyReservation);

        //when
        List<Reservation> userReservations = userDao.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId())).getListOfReservations();
        List<Reservation> travelReservations = travelDao.findById(travel.getId()).orElseThrow(() -> new TravelNotFoundException(travel.getId())).getListOfReservations();
        List<Reservation> skyReservations = travelSkyDao.findById(travelSky.getId()).orElseThrow(() -> new TravelSkyNotFoundException(travelSky.getId())).getListOfReservations();

        //then
        assertEquals(2, userReservations.size());
        assertEquals(1, travelReservations.size());
        assertEquals(1, skyReservations.size());
    }
}
