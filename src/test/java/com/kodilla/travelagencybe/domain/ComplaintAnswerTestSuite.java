package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.exception.ComplaintAnswerNotFoundException;
import com.kodilla.travelagencybe.exception.ComplaintNotFoundException;
import com.kodilla.travelagencybe.repository.*;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        reservationDao.save(reservation);
        Complaint complaint = new Complaint(null, reservation, "compliant description",
                timeProvider.getTime(), null, null, Status.OPENED);
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(null, complaint, "answer", timeProvider.getTime());

        //when
        ComplaintAnswer savedCompliantAnswer = complaintAnswerDao.save(compliantAnswer);

        //then
        assertNotEquals(0, savedCompliantAnswer.getId());
    }

    @Test
    public void testReadComplaintAnswer() throws Exception {
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
        reservationDao.save(reservation);
        Complaint complaint = new Complaint(null, reservation, "compliant description",
                timeProvider.getTime(), null, null, Status.OPENED);
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(null, complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
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
        reservationDao.save(reservation);
        Complaint complaint = new Complaint(null, reservation, "compliant description",
                timeProvider.getTime(), null, null, Status.OPENED);
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(null, complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
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
        reservationDao.save(reservation);
        Complaint complaint = new Complaint(null, reservation, "compliant description",
                timeProvider.getTime(), null, null, Status.OPENED);
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(null, complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
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
        reservationDao.save(reservation);
        Complaint complaint = new Complaint(null, reservation, "compliant description",
                timeProvider.getTime(), null, null, Status.OPENED);
        complaintDao.save(complaint);
        ComplaintAnswer compliantAnswer = new ComplaintAnswer(null, complaint, "answer", LocalDateTime.of(2021, 11, 27, 21, 10));
        complaintAnswerDao.save(compliantAnswer);

        //when
        ComplaintAnswer answerFromComplaint = complaintDao.findById(complaint.getId()).orElseThrow(() -> new ComplaintNotFoundException(complaint.getId())).getComplaintAnswer();

        //then
        assertEquals(compliantAnswer.getId(), answerFromComplaint.getId());
        assertEquals("answer", answerFromComplaint.getAnswer());
        assertEquals(LocalDateTime.of(2021, 11, 27, 21, 10), answerFromComplaint.getCreationDate());
    }
}
