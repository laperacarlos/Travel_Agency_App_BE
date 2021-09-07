package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.TravelSky;
import com.kodilla.travelagencybe.domain.TravelSkyDto;
import com.kodilla.travelagencybe.mapper.TravelSkyMapper;
import com.kodilla.travelagencybe.service.TravelSkyService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TravelSkyController {
    TravelSkyService travelSkyService;
    TravelSkyMapper travelSkyMapper;
     TimeProvider timeProvider;

    public void createTravelSky(final TravelSkyDto travelSkyDto) {
        TravelSky travelSky = travelSkyMapper.mapToTravelSky(travelSkyDto);
        travelSky.setCreationDate(timeProvider.getTime());
        travelSkyService.saveTravelSky(travelSky);
    }

    public void updateTravelSky(final TravelSkyDto travelSkyDto) {
        travelSkyService.saveTravelSky(travelSkyMapper.mapToTravelSky(travelSkyDto));
    }

}
