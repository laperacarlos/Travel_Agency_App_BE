package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.TravelSky;
import com.kodilla.travelagencybe.domain.TravelSkyDto;
import com.kodilla.travelagencybe.exception.TravelSkyNotFoundException;
import com.kodilla.travelagencybe.mapper.TravelSkyMapper;
import com.kodilla.travelagencybe.service.TravelSkyService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class TravelSkyController {
    private final TravelSkyService travelSkyService;
    private final TravelSkyMapper travelSkyMapper;
    private final TimeProvider timeProvider;

    @PostMapping(value = "travelSky", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTravelSky(@RequestBody TravelSkyDto travelSkyDto) {
        TravelSky travelSky = travelSkyMapper.mapToTravelSky(travelSkyDto);
        travelSky.setCreationDate(timeProvider.getTime());
        travelSkyService.saveTravelSky(travelSky);
    }

    @PutMapping(value = "travelSky")
    public void updateTravelSky(@RequestBody TravelSkyDto travelSkyDto) throws TravelSkyNotFoundException {
        if (travelSkyService.getTravelSkyById(travelSkyDto.getId()).isPresent()) {
            travelSkyService.saveTravelSky(travelSkyMapper.mapToTravelSky(travelSkyDto));
        } else throw new TravelSkyNotFoundException(travelSkyDto.getId());
    }

    @GetMapping(value = "travelSky")
    public List<TravelSkyDto> getAllTravelSky() {
        return travelSkyMapper.mapToTravelSkyDtoList(travelSkyService.getAllSkyTravels());
    }
}
