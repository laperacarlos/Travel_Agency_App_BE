package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelSkyMapper {

    public TravelSky mapToTravelSky(final TravelSkyDto travelSkyDto) {
        return new TravelSky(
                travelSkyDto.getId(),
                travelSkyDto.getOrigin(),
                travelSkyDto.getDestination(),
                travelSkyDto.getDepartureDate(),
                travelSkyDto.getReturnDate(),
                travelSkyDto.getStatus(),
                travelSkyDto.getCreationDate(),
                travelSkyDto.getListOfReservations()
        );
    }

    public TravelSkyDto mapToTravelSkyDto(final TravelSky travelSky) {
        return new TravelSkyDto(
                travelSky.getId(),
                travelSky.getOrigin(),
                travelSky.getDestination(),
                travelSky.getDepartureDate(),
                travelSky.getReturnDate(),
                travelSky.getStatus(),
                travelSky.getCreationDate(),
                travelSky.getListOfReservations()
        );
    }

    public List<TravelSkyDto> mapToTravelSkyDtoList(final List<TravelSky> travelSkyList) {
        return travelSkyList.stream()
                .map(this::mapToTravelSkyDto)
                .collect(Collectors.toList());
    }
}
