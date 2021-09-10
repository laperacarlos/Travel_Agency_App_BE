package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.accuweather.client.AccuWeatherClient;
import com.kodilla.travelagencybe.domain.weather.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccuWeatherService {
    private final AccuWeatherClient accuWeatherClient;

    public WeatherDto checkWeather(final String city) throws Exception {
        return accuWeatherClient.checkWeather(city);
    }
}
