package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.exception.ReservationNotFoundException;
import com.kodilla.travelagencybe.exception.TravelNotFoundException;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.repository.ReservationDao;
import com.kodilla.travelagencybe.repository.TravelDao;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    TimeProvider timeProvider;

    @AfterEach
    public void databaseCleaning() {
        reservationDao.deleteAll();
        travelDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testCreateReservation() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);
        Reservation reservation = new Reservation(null, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);

        //when
        Reservation savedReservation = reservationDao.save(reservation);

        //then
        assertNotEquals(0L, savedReservation.getId());
    }

    @Test
    public void testReadReservation() throws Exception {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);
        Reservation travelReservation = new Reservation(null, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2021, 8, 19, 14, 10), Status.OPENED);
        reservationDao.save(travelReservation);

        //when
        Reservation travelReservationFromDb = reservationDao.findById(travelReservation.getId()).orElseThrow(() -> new ReservationNotFoundException(travelReservation.getId()));

        //then
        assertEquals(user.getId(), travelReservationFromDb.getUser().getId());
        assertEquals(travel.getId(), travelReservationFromDb.getTravel().getId());
        assertEquals(TravelType.BASIC, travelReservationFromDb.getTravelType());
        assertEquals(HotelStandard.FOUR, travelReservationFromDb.getHotelStandard());
        assertEquals(MealStandard.ALL_INCLUSIVE, travelReservationFromDb.getMealStandard());
        assertEquals(LocalDateTime.of(2021, 8, 19, 14, 10), travelReservationFromDb.getCreationDate());
        assertEquals(Status.OPENED, travelReservationFromDb.getStatus());
    }

    @Test
    public void testUpdateReservation() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);
        Reservation travelReservation = new Reservation(null, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        reservationDao.save(travelReservation);

        //when
        travelReservation.setTravelType(TravelType.SIGHTSEEING);
        travelReservation.setHotelStandard(HotelStandard.ONE);
        travelReservation.setMealStandard(MealStandard.EXCLUDED);
        travelReservation.setStatus(Status.CANCELED);
        travelReservation.setCreationDate(LocalDateTime.of(2021, 8, 22, 15, 10));
        Reservation updatedTravelReservation = reservationDao.save(travelReservation);

        //then
        assertEquals(TravelType.SIGHTSEEING, updatedTravelReservation.getTravelType());
        assertEquals(HotelStandard.ONE, updatedTravelReservation.getHotelStandard());
        assertEquals(MealStandard.EXCLUDED, updatedTravelReservation.getMealStandard());
        assertEquals(Status.CANCELED, updatedTravelReservation.getStatus());
        assertEquals(LocalDateTime.of(2021, 8, 22, 15, 10), updatedTravelReservation.getCreationDate());
    }

    @Test
    public void testDeleteReservation() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);
        Reservation travelReservation = new Reservation(null, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        reservationDao.save(travelReservation);

        //when
        reservationDao.deleteById(travelReservation.getId());
        Optional<Reservation> optTravelReservation = reservationDao.findById(travelReservation.getId());

        //then
        assertFalse(optTravelReservation.isPresent());
    }

    @Test
    public void testRelationsWithEntities() throws Exception {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);
        Travel travel = new Travel(null, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, timeProvider.getTime(), new ArrayList<>());
        travelDao.save(travel);
        Reservation travelReservation = new Reservation(null, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, timeProvider.getTime(), Status.OPENED);
        reservationDao.save(travelReservation);

        //when
        List<Reservation> userReservations = userDao.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId())).getListOfReservations();
        List<Reservation> travelReservations = travelDao.findById(travel.getId()).orElseThrow(() -> new TravelNotFoundException(travel.getId())).getListOfReservations();

        //then
        assertEquals(1, userReservations.size());
        assertEquals(1, travelReservations.size());
    }
}
