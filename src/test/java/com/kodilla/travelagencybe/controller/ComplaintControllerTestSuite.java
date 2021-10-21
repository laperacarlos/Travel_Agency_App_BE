package com.kodilla.travelagencybe.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.travelagencybe.domain.*;
import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.gson.LocalDateSerializer;
import com.kodilla.travelagencybe.gson.LocalDateTimeSerializer;
import com.kodilla.travelagencybe.mapper.ComplaintMapper;
import com.kodilla.travelagencybe.service.ComplaintService;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ComplaintController.class)
public class ComplaintControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComplaintService complaintService;

    @MockBean
    private ComplaintMapper complaintMapper;

    @Test
    void shouldCreateComplaint() throws Exception {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());
        Reservation reservation = new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        Complaint complaint = new Complaint(1L, reservation, "complaint description",
                LocalDateTime.of(2020, 11, 7, 7, 7), null, null, Status.OPENED);

        ComplaintDto complaintDto = new ComplaintDto(1L, reservation, "complaint description",
                LocalDateTime.of(2020, 11, 7, 7, 7), null, null, Status.OPENED);

        when(complaintMapper.mapToComplaint(complaintDto)).thenReturn(complaint);
        when(complaintMapper.mapToComplaintDto(complaintService.saveNewCompliant(complaint))).thenReturn(complaintDto);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(complaintDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("complaint description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OPENED")));
    }

    @Test
    void shouldUpdateComplaint() throws Exception {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Travel travel = new Travel(1L, "Warsaw", "Boston",
                LocalDate.of(2021, 11, 7),
                LocalDate.of(2021, 11, 27),
                Status.OPENED, LocalDateTime.of(2020, 11, 7, 7, 7), new ArrayList<>());
        Reservation reservation = new Reservation(1L, user, travel,
                TravelType.BASIC, HotelStandard.FOUR, MealStandard.ALL_INCLUSIVE,
                null, LocalDateTime.of(2020, 11, 7, 7, 7), Status.OPENED);

        Complaint complaint = new Complaint(1L, reservation, "complaint description",
                LocalDateTime.of(2020, 11, 7, 7, 7), null, null, Status.OPENED);

        ComplaintDto complaintDto = new ComplaintDto(1L, reservation, "complaint description",
                LocalDateTime.of(2020, 11, 7, 7, 7), null, null, Status.OPENED);

        when(complaintService.getComplaintById(complaintDto.getId())).thenReturn(Optional.of(complaint));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(complaintDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
