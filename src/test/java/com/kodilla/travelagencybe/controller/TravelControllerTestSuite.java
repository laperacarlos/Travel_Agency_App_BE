package com.kodilla.travelagencybe.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.TravelDto;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.gson.LocalDateSerializer;
import com.kodilla.travelagencybe.gson.LocalDateTimeSerializer;
import com.kodilla.travelagencybe.mapper.TravelMapper;
import com.kodilla.travelagencybe.service.TravelService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TravelController.class)
public class TravelControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @MockBean
    private TravelMapper travelMapper;

    @Test
    void shouldCreateTravel() throws Exception {
        //given
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        TravelDto traveldto = new TravelDto(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        when(travelMapper.mapToTravel(traveldto)).thenReturn(travel);
        when(travelMapper.mapToTravelDto(travelService.saveNewTravel(travel))).thenReturn(traveldto);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(traveldto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin", Matchers.is("Warsaw")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination", Matchers.is("Boston")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", Matchers.is("2021-11-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate", Matchers.is("2021-11-27")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OPENED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfReservations", Matchers.hasSize(0)));
    }

    @Test
    void shouldUpdateTravel() throws Exception {
        //given
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        TravelDto traveldto = new TravelDto(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        when(travelMapper.mapToTravel(traveldto)).thenReturn(travel);
        when(travelMapper.mapToTravelDto(travelService.saveTravel(travel))).thenReturn(traveldto);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(traveldto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetAllTravels() throws Exception {
        //given
        List<TravelDto> travelDtoList = List.of(new TravelDto(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>()));

        when(travelMapper.mapToTravelDtoList(travelService.getAllTravels())).thenReturn(travelDtoList);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/travelAgencyBe/v1/travels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin", Matchers.is("Warsaw")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination", Matchers.is("Boston")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDate", Matchers.is("2021-11-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].returnDate", Matchers.is("2021-11-27")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("OPENED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listOfReservations", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetOpenTravels() throws Exception {
        //given
        List<TravelDto> travelDtoList = List.of(new TravelDto(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>()));

        when(travelMapper.mapToTravelDtoList(travelService.getOpenTravels())).thenReturn(travelDtoList);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/travelAgencyBe/v1/travels/open")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin", Matchers.is("Warsaw")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination", Matchers.is("Boston")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDate", Matchers.is("2021-11-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].returnDate", Matchers.is("2021-11-27")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("OPENED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listOfReservations", Matchers.hasSize(0)));
    }
}
