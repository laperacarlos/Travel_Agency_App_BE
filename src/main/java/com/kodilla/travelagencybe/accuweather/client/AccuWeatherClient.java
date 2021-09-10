package com.kodilla.travelagencybe.accuweather.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.travelagencybe.accuweather.config.AccuWeatherConfig;
import com.kodilla.travelagencybe.domain.weather.WeatherDto;
import com.kodilla.travelagencybe.exception.LocationNotFoundException;
import com.kodilla.travelagencybe.exception.WeatherNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccuWeatherClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccuWeatherClient.class);
    private final RestTemplate restTemplate;
    private final AccuWeatherConfig accuWeatherConfig;

    public WeatherDto checkWeather(String cityName) throws Exception {
        URI url = UriComponentsBuilder.fromHttpUrl(accuWeatherConfig.getAccuweatherEndpoint() + "currentconditions/v1/" + getLocationKey(cityName))
                .queryParam("apikey", accuWeatherConfig.getAccuweatherApiKey())
                .build()
                .encode()
                .toUri();

        try {
            WeatherDto[] fetchedWeather = restTemplate.getForObject(url, WeatherDto[].class);
            WeatherDto[] weather = Optional.ofNullable(fetchedWeather).orElse(new WeatherDto[]{});
            if (weather.length == 0) {
                throw new WeatherNotFoundException(cityName);
            } else return weather[0];
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }


    private String getLocationKey(String cityName) throws LocationNotFoundException {
        URI url = UriComponentsBuilder.fromHttpUrl(accuWeatherConfig.getAccuweatherEndpoint() + "locations/v1/cities/search")
                .queryParam("apikey", accuWeatherConfig.getAccuweatherApiKey())
                .queryParam("q", cityName)
                .build()
                .encode()
                .toUri();

        try {
            Location[] locations = restTemplate.getForObject(url, Location[].class);
            Location[] optLocation = Optional.ofNullable(locations).orElse(new Location[]{});
            if(optLocation.length == 0) {
                throw new LocationNotFoundException(cityName);
            } else return locations[0].key;
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Location {
        @JsonProperty("Key")
        public String key;
    }
}
