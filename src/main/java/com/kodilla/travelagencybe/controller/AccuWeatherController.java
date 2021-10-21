package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.weather.WeatherDto;
import com.kodilla.travelagencybe.service.AccuWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1/accuweather")
@RequiredArgsConstructor
public class AccuWeatherController {

    private final AccuWeatherService accuWeatherService;

    @GetMapping("check")
    public WeatherDto checkWeather(@RequestParam String city) throws Exception {
        return accuWeatherService.checkWeather(city);
    }
}
