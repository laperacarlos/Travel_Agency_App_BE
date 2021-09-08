package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.Reservation;
import com.kodilla.travelagencybe.domain.ReservationDto;
import com.kodilla.travelagencybe.exception.ReservationNotFoundException;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.mapper.ReservationMapper;
import com.kodilla.travelagencybe.service.ReservationService;
import com.kodilla.travelagencybe.service.UserService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationMapper reservationMapper;
    private final ReservationService reservationService;
    private final UserService userService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReservation (@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservation.setCreationDate(timeProvider.getTime());
        reservationService.saveReservation(reservation);
    }

    @PutMapping(value = "reservations")
    public void updateReservation (@RequestBody ReservationDto reservationDto) throws ReservationNotFoundException {
        if(reservationService.getReservationById(reservationDto.getId()).isPresent()) {
            reservationService.saveReservation(reservationMapper.mapToReservation(reservationDto));
        } else throw new ReservationNotFoundException(reservationDto.getId());
    }

    @GetMapping(value = "reservations")
    public List<ReservationDto> getAllReservations() {
        return reservationMapper.mapToReservationDtoList(reservationService.getAllReservations());
    }

    @GetMapping(value = "reservations/user/{userId}")
    public List<ReservationDto> getUserReservations(@PathVariable Long userId) throws UserNotFoundException {
        return reservationMapper.mapToReservationDtoList(userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException(userId)).getListOfReservations());
    }
}
