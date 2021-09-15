package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.Reservation;
import com.kodilla.travelagencybe.repository.ReservationDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeProvider timeProvider;

    public Reservation saveReservation(final Reservation reservation) {
        return reservationDao.save(reservation);
    }

    public Reservation saveNewReservation(final Reservation reservation) {
        reservation.setCreationDate(timeProvider.getTime());
        return reservationDao.save(reservation);
    }

    public Optional<Reservation> getReservationById(final Long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }
}
