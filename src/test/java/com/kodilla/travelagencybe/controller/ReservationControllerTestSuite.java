package com.kodilla.travelagencybe.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.travelagencybe.domain.Reservation;
import com.kodilla.travelagencybe.domain.ReservationDto;
import com.kodilla.travelagencybe.domain.Travel;
import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.gson.LocalDateSerializer;
import com.kodilla.travelagencybe.gson.LocalDateTimeSerializer;
import com.kodilla.travelagencybe.mapper.ReservationMapper;
import com.kodilla.travelagencybe.service.ReservationService;
import com.kodilla.travelagencybe.service.UserService;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ReservationController.class)
public class ReservationControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationMapper reservationMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateReservation() throws Exception {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        Reservation reservation = new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        ReservationDto reservationDto = new ReservationDto(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservationService.saveNewReservation(reservation))).thenReturn(reservationDto);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(reservationDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.travel.origin", Matchers.is("Warsaw")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.travelType", Matchers.is("BASIC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotelStandard", Matchers.is("FOUR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mealStandard", Matchers.is("ALL_INCLUSIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OPENED")));
    }

    @Test
    void shouldUpdateReservation() throws Exception {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        Reservation reservation = new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        ReservationDto reservationDto = new ReservationDto(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        when(reservationService.getReservationById(reservationDto.getId())).thenReturn(Optional.of(reservation));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(reservationDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/travelAgencyBe/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetAllReservations() throws Exception {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());

        List<ReservationDto> reservationDtoList = List.of(new ReservationDto(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED));

        when(reservationMapper.mapToReservationDtoList(reservationService.getAllReservations())).thenReturn(reservationDtoList);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/travelAgencyBe/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].travel.origin", Matchers.is("Warsaw")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].travelType", Matchers.is("BASIC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hotelStandard", Matchers.is("FOUR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mealStandard", Matchers.is("ALL_INCLUSIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("OPENED")));
    }
}
