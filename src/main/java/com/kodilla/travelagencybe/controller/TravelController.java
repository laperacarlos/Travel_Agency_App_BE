package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.TravelDto;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.mapper.TravelMapper;
import com.kodilla.travelagencybe.service.TravelService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe")
@RequiredArgsConstructor
public class TravelController {
    private final TravelMapper travelMapper;
    private final TravelService travelService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "travels", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTravel(@RequestBody TravelDto travelDto) {
        Travel travel = travelMapper.mapToTravel(travelDto);
        travel.setCreationDate(timeProvider.getTime());
        travelService.saveTravel(travel);
    }

    @PutMapping(value = "travels")
    public void updateTravel(@RequestBody TravelDto travelDto) {
        travelService.saveTravel(travelMapper.mapToTravel(travelDto));
    }

    @GetMapping(value = "travels")
    public List<TravelDto> getAllTravels() {
        return travelMapper.mapToTravelDtoList(travelService.getAllTravels());
    }

    @GetMapping(value = "travels/active")
    public List<TravelDto> getActiveTravels() {
        return travelMapper.mapToTravelDtoList(travelService.getAllTravels().stream()
                .filter(travel -> travel.getStatus().equals(Status.OPENED))
                .collect(Collectors.toList()));
    }
}
