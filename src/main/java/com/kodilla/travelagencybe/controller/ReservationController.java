package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.Reservation;
import com.kodilla.travelagencybe.domain.ReservationDto;
import com.kodilla.travelagencybe.mapper.ReservationMapper;
import com.kodilla.travelagencybe.service.ReservationService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationMapper reservationMapper;
    private final ReservationService reservationService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReservation (@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservation.setCreationDate(timeProvider.getTime());
        reservationService.saveReservation(reservation);
    }

    @PutMapping(value = "reservations")
    public void updateReservation (@RequestBody ReservationDto reservationDto) {
        reservationService.saveReservation(reservationMapper.mapToReservation(reservationDto));
    }
}
