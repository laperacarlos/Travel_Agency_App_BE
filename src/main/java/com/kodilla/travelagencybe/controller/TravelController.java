package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.TravelDto;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.exception.TravelNotFoundException;
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
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class TravelController {
    private final TravelMapper travelMapper;
    private final TravelService travelService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "travels", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TravelDto createTravel(@RequestBody TravelDto travelDto) {
        Travel travel = travelMapper.mapToTravel(travelDto);
        travel.setCreationDate(timeProvider.getTime());
        return travelMapper.mapToTravelDto(travelService.saveTravel(travel));
    }

    @PutMapping(value = "travels")
    public void updateTravel(@RequestBody TravelDto travelDto) throws TravelNotFoundException {
        if(travelService.getTravelById(travelDto.getId()).isPresent()) {
            travelService.saveTravel(travelMapper.mapToTravel(travelDto));
        } else throw new TravelNotFoundException(travelDto.getId());
    }

    @GetMapping(value = "travels")
    public List<TravelDto> getAllTravels() {
        return travelMapper.mapToTravelDtoList(travelService.getAllTravels());
    }

    @GetMapping(value = "travels/open")
    public List<TravelDto> getOpenTravels() {
        return travelMapper.mapToTravelDtoList(travelService.getAllTravels().stream()
                .filter(travel -> travel.getStatus().equals(Status.OPENED))
                .collect(Collectors.toList()));
    }
}
