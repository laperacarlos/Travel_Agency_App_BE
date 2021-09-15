package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.repository.TravelDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelDao travelDao;
    private final TimeProvider timeProvider;

    public Travel saveNewTravel(final Travel travel) {
        travel.setCreationDate(timeProvider.getTime());
        return travelDao.save(travel);
    }

    public Travel saveTravel(final Travel travel) {
        return travelDao.save(travel);
    }

    public Optional<Travel> getTravelById(final Long id) {
        return travelDao.findById(id);
    }

    public List<Travel> getAllTravels() {
        return travelDao.findAll();
    }

    public List<Travel> getOpenTravels() {
        return travelDao.findAll().stream()
                .filter(travel -> travel.getStatus().equals(Status.OPENED))
                .collect(Collectors.toList());
    }
}
