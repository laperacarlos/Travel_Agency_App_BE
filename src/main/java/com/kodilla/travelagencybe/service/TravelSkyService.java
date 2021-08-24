package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.TravelSky;
import com.kodilla.travelagencybe.repository.TravelSkyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelSkyService {

    private final TravelSkyDao travelSkyDao;

    public TravelSky saveTravelSky(final TravelSky travelSky) {
        return travelSkyDao.save(travelSky);
    }

    public Optional<TravelSky> getTravelSkyById(final Long id) {
        return travelSkyDao.findById(id);
    }

    public List<TravelSky> getAllSkyTravels() {
        return travelSkyDao.findAll();
    }
}
