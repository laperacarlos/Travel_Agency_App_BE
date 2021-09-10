package com.kodilla.travelagencybe.accuweather.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AccuWeatherConfig {

    @Value("http://dataservice.accuweather.com/")
    private String accuweatherEndpoint;

    @Value("E6OjF6Mz4WT5Tv4bNEb8ATe9h9HhObwp")
    private String accuweatherApiKey;
}
