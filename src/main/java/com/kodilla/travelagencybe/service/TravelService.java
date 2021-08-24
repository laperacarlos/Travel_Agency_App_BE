package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.repository.TravelDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelDao travelDao;

    public Travel saveTravel(final Travel travel) {
        return travelDao.save(travel);
    }

    public Optional<Travel> getTravelById(final Long id) {
        return travelDao.findById(id);
    }

    public List<Travel> getAllTravels() {
        return travelDao.findAll();
    }
}
