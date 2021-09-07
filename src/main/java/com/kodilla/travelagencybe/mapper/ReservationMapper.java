package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.Reservation;
import com.kodilla.travelagencybe.domain.ReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationMapper {

    public Reservation mapToReservation(final ReservationDto reservationDto) {
        return new Reservation(
                reservationDto.getId(),
                reservationDto.getUser(),
                reservationDto.getTravel(),
                reservationDto.getTravelSky(),
                reservationDto.getTravelType(),
                reservationDto.getHotelStandard(),
                reservationDto.getMealStandard(),
                reservationDto.getComplaint(),
                reservationDto.getCreationDate(),
                reservationDto.getStatus()
        );
    }

    public ReservationDto mapToReservationDto(final Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getUser(),
                reservation.getTravel(),
                reservation.getTravelSky(),
                reservation.getTravelType(),
                reservation.getHotelStandard(),
                reservation.getMealStandard(),
                reservation.getComplaint(),
                reservation.getCreationDate(),
                reservation.getStatus()
        );
    }

    public List<ReservationDto> mapToReservationDtoList(final List<Reservation> reservationListList) {
        return reservationListList.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }
}
