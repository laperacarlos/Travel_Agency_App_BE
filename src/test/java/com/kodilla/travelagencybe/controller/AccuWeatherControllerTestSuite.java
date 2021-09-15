package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.weather.MetricDto;
import com.kodilla.travelagencybe.domain.weather.TemperatureDto;
import com.kodilla.travelagencybe.domain.weather.WeatherDto;
import com.kodilla.travelagencybe.service.AccuWeatherService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(AccuWeatherController.class)
public class AccuWeatherControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccuWeatherService accuWeatherService;

    @Test
    void shouldCheckAccuWeather() throws Exception{
        //given
        MetricDto metricDto = new MetricDto(25.5);
        TemperatureDto temperatureDto = new TemperatureDto(metricDto);
        WeatherDto weatherDto = new WeatherDto("Sunny", temperatureDto);

        when(accuWeatherService.checkWeather(anyString())).thenReturn(weatherDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                         .get("/travelAgencyBe/v1/accuweather/check")
                         .param("city", "Warsaw")
                         .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.WeatherText", Matchers.is("Sunny")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Temperature.Metric.Value", Matchers.is(25.5)));
    }
}
