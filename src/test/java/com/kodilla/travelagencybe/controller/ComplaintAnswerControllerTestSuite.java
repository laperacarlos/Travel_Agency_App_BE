package com.kodilla.travelagencybe.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.travelagencybe.domain.*;
import com.kodilla.travelagencybe.enums.*;
import com.kodilla.travelagencybe.gson.LocalDateSerializer;
import com.kodilla.travelagencybe.gson.LocalDateTimeSerializer;
import com.kodilla.travelagencybe.mapper.ComplaintAnswerMapper;
import com.kodilla.travelagencybe.service.ComplaintAnswerService;
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

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(ComplaintAnswerController.class)
public class ComplaintAnswerControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComplaintAnswerService complaintAnswerService;

    @MockBean
    private ComplaintAnswerMapper complaintAnswerMapper;

    @Test
    void shouldCreateComplaintAnswer() throws Exception {
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

        ComplaintAnswer complaintAnswer = new ComplaintAnswer(1L, complaint, "answer",
                LocalDateTime.of(2020, 11, 7, 7, 7));

        ComplaintAnswerDto complaintAnswerDto = new ComplaintAnswerDto(1L, complaint, "answer",
                LocalDateTime.of(2020, 11, 7, 7, 7));

        when(complaintAnswerMapper.mapToComplaintAnswer(complaintAnswerDto)).thenReturn(complaintAnswer);
        when(complaintAnswerMapper.mapToComplaintAnswerDto(complaintAnswerService.saveNewAnswer(complaintAnswer))).thenReturn(complaintAnswerDto);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(complaintAnswerDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/complaints/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.complaint.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answer", Matchers.is("answer")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")));
    }

    @Test
    void shouldUpdateComplaintAnswer() throws Exception {
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

        ComplaintAnswer complaintAnswer = new ComplaintAnswer(1L, complaint, "answer",
                LocalDateTime.of(2021, 11, 27, 21, 10));

        ComplaintAnswerDto complaintAnswerDto = new ComplaintAnswerDto(1L, complaint, "answer",
                LocalDateTime.of(2021, 11, 27, 21, 10));

        when(complaintAnswerService.getAnswerById(complaintAnswerDto.getId())).thenReturn(Optional.of(complaintAnswer));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(complaintAnswerDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/complaints/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteComplaintAnswer() throws Exception {
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

        ComplaintAnswer complaintAnswer = new ComplaintAnswer(1L, complaint, "answer",
                LocalDateTime.of(2021, 11, 27, 21, 10));

        when(complaintAnswerService.getAnswerById(anyLong())).thenReturn(Optional.of(complaintAnswer));
        doNothing().when(complaintAnswerService).deleteById(anyLong());
        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/travelAgencyBe/v1/complaints/answers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
