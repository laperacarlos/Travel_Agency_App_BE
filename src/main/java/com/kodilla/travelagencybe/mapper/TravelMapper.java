package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.TravelDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelMapper {

    public Travel mapToTravel(final TravelDto travelDto) {
        return new Travel(
                travelDto.getId(),
                travelDto.getOrigin(),
                travelDto.getDestination(),
                travelDto.getDepartureDate(),
                travelDto.getReturnDate(),
                travelDto.getStatus(),
                travelDto.getCreationDate(),
                travelDto.getListOfReservations()
        );
    }

    public TravelDto mapToTravelDto(final Travel travel) {
        return new TravelDto(
                travel.getId(),
                travel.getOrigin(),
                travel.getDestination(),
                travel.getDepartureDate(),
                travel.getReturnDate(),
                travel.getStatus(),
                travel.getCreationDate(),
                travel.getListOfReservations()
        );
    }

    public List<TravelDto> mapToTravelDtoList(final List<Travel> travelList) {
        return travelList.stream()
                .map(this::mapToTravelDto)
                .collect(Collectors.toList());
    }
}
