package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.TravelType;
import com.kodilla.travelagencybe.exception.ComplaintAnswerNotFoundException;
import com.kodilla.travelagencybe.exception.ComplaintNotFoundException;
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
public class ComplaintAnswerTestSuite {

    @Autowired
    ComplaintAnswerDao complaintAnswerDao;

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
        complaintAnswerDao.deleteAll();
        complaintDao.deleteAll();
        reservationDao.deleteAll();
        travelDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testCreateComplaintAnswer() {
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
        Complaint compliant = new Complaint(reservation, "complaint description", timeProvider.getTime());
        complaintDao.save(compliant);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(compliant, "answer", timeProvider.getTime());

        //when
        ComplaintAnswer savedCompliantAnswer = complaintAnswerDao.save(compliantAnswer);

        //then
        assertNotEquals(0, savedCompliantAnswer.getId());
    }

    @Test
    public void testReadComplaintAnswer() throws Exception {
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
        Complaint complaint = new Complaint(reservation, "complaint description", timeProvider.getTime());
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintAnswerDao.save(compliantAnswer);

        //when
        ComplaintAnswer complaintAnswerFromDb = complaintAnswerDao.findById(compliantAnswer.getId()).orElseThrow(() -> new ComplaintAnswerNotFoundException(compliantAnswer.getId()));

        //then
        assertEquals(complaint.getId(), complaintAnswerFromDb.getComplaint().getId());
        assertEquals("answer", complaintAnswerFromDb.getAnswer());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), complaintAnswerFromDb.getCreationDate());
    }

    @Test
    public void testUpdateComplaintAnswer() {
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
        Complaint complaint = new Complaint(reservation, "complaint description", timeProvider.getTime());
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintAnswerDao.save(compliantAnswer);

        //when
        compliantAnswer.setAnswer("new answer");
        compliantAnswer.setCreationDate(LocalDateTime.of(2021, 11, 19, 17, 0));
        ComplaintAnswer updatedCompliantAnswer = complaintAnswerDao.save(compliantAnswer);

        //then
        assertEquals("new answer", updatedCompliantAnswer.getAnswer());
        assertEquals(LocalDateTime.of(2021, 11, 19, 17, 0), updatedCompliantAnswer.getCreationDate());
    }

    @Test
    public void testDeleteComplaintAnswer() {
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
        Complaint complaint = new Complaint(reservation, "complaint description", timeProvider.getTime());
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintAnswerDao.save(compliantAnswer);

        //when
        complaintAnswerDao.deleteById(compliantAnswer.getId());
        Optional<ComplaintAnswer> optComplaintAnswer = complaintAnswerDao.findById(compliantAnswer.getId());

        //then
        assertFalse(optComplaintAnswer.isPresent());
    }

    @Test
    public void testRelationWithComplaint() throws Exception {
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
        Complaint complaint = new Complaint(reservation, "complaint description", timeProvider.getTime());
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintAnswerDao.save(compliantAnswer);

        //when
        ComplaintAnswer answerFromComplaint = complaintDao.findById(complaint.getId()).orElseThrow(() -> new ComplaintNotFoundException(complaint.getId())).getComplaintAnswer();

        //then
        assertEquals(compliantAnswer.getId(), answerFromComplaint.getId());
        assertEquals("answer", answerFromComplaint.getAnswer());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), answerFromComplaint.getCreationDate());
    }
}
