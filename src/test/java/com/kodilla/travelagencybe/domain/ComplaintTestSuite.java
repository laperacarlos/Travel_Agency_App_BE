package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.enums.TravelType;
import com.kodilla.travelagencybe.exception.ComplaintNotFoundException;
import com.kodilla.travelagencybe.exception.ReservationNotFoundException;
import com.kodilla.travelagencybe.repository.*;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComplaintTestSuite {

    @Autowired
    ComplaintDao complaintDao;

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
        complaintDao.deleteAll();
        reservationDao.deleteAll();
        travelDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testCreateCompliant() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        reservationDao.save(reservation);
        Complaint compliant = new Complaint(reservation, "compliant description", timeProvider.getTime());

        //when
        Complaint savedCompliant = complaintDao.save(compliant);

        //then
        assertNotEquals(0, savedCompliant.getId());
    }

    @Test
    public void testReadCompliant() throws Exception {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        reservationDao.save(reservation);
        Complaint compliant = new Complaint(reservation, "compliant description", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintDao.save(compliant);

        //when
        Complaint compliantFromDb = complaintDao.findById(compliant.getId()).orElseThrow(() -> new ComplaintNotFoundException(compliant.getId()));

        //then
        assertEquals(reservation.getId(), compliantFromDb.getReservation().getId());
        assertEquals("compliant description", compliantFromDb.getDescription());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), compliantFromDb.getCreationDate());
        assertEquals(Status.OPENED, compliantFromDb.getStatus());
        assertNull(compliantFromDb.getClosingDate());
    }

    @Test
    public void testUpdateCompliant() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        reservationDao.save(reservation);
        Complaint compliant = new Complaint(reservation, "compliant description", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintDao.save(compliant);

        //when
        compliant.setDescription("changed description");
        compliant.setClosingDate(LocalDateTime.of(2021, 12, 24, 18, 10));
        compliant.setStatus(Status.CANCELED);
        Complaint updatedCompliant = complaintDao.save(compliant);

        //then
        assertEquals("changed description", updatedCompliant.getDescription());
        assertEquals(LocalDateTime.of(2021, 12, 24, 18, 10), updatedCompliant.getClosingDate());
        assertEquals(Status.CANCELED, updatedCompliant.getStatus());
    }

    @Test
    public void testDeleteCompliant() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        reservationDao.save(reservation);
        Complaint compliant = new Complaint(reservation, "compliant description", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintDao.save(compliant);

        //when
        complaintDao.deleteById(compliant.getId());
        Optional<Complaint> optComplaint = complaintDao.findById(compliant.getId());

        //then
        assertFalse(optComplaint.isPresent());
    }

    @Test
    public void testRelationWithReservation() throws Exception {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);
        Travel travel = new Travel("Warsaw", "Boston",
                LocalDateTime.of(2021, 11, 7, 21, 10),
                LocalDateTime.of(2021, 11, 27, 21, 10),
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE_ALC_FREE,
                timeProvider.getTime());
        travelDao.save(travel);
        Reservation reservation = new Reservation(user, travel, timeProvider.getTime());
        reservationDao.save(reservation);
        Complaint compliant = new Complaint(reservation, "compliant description", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintDao.save(compliant);

        //when
        Complaint reservationComplaint = reservationDao.findById(reservation.getId()).orElseThrow(() -> new ReservationNotFoundException(reservation.getId())).getComplaint();

        //then
        assertEquals(compliant.getId(), reservationComplaint.getId());
        assertEquals("compliant description", reservationComplaint.getDescription());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), reservationComplaint.getCreationDate());
        assertEquals(Status.OPENED, reservationComplaint.getStatus());
        assertNull(reservationComplaint.getClosingDate());
    }
}
