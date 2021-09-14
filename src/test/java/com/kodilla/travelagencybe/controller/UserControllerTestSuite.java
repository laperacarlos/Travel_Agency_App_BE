package com.kodilla.travelagencybe.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.travelagencybe.enums.UserStatus;
import com.kodilla.travelagencybe.gson.LocalDateTimeSerializer;
import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.domain.UserDto;
import com.kodilla.travelagencybe.mapper.UserMapper;
import com.kodilla.travelagencybe.service.UserService;
import com.kodilla.travelagencybe.utility.TimeProvider;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TimeProvider timeProvider;

    @MockBean
    private UserMapper userMapper;

    @Test
    void shouldGetListOfAllUsers() throws Exception{
        //given
        List<User> userList = List.of(new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>()));
        List<UserDto> mappedUserList = List.of(new UserDto(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>()));

        when(userService.getAllUsers()).thenReturn(userList);
        when(userMapper.mapToUserDtoList(userList)).thenReturn(mappedUserList);
        when(timeProvider.getTime()).thenReturn(LocalDateTime.of(2020, 11, 7, 7, 7));

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                         .get("/travelAgencyBe/v1/users")
                         .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("username")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("email@com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isActive", Matchers.is("YES")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isAdministrator", Matchers.is("NO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listOfReservations", Matchers.hasSize(0)));
    }

    @Test
    void shouldCreateUser() throws Exception{
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        UserDto userDto = new UserDto(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userMapper.mapToUserDto(userService.saveUser(user))).thenReturn(userDto);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(userDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/travelAgencyBe/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("username")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("email@com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive", Matchers.is("YES")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAdministrator", Matchers.is("NO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfReservations", Matchers.hasSize(0)));
    }

    @Test
    void shouldUpdateUser() throws Exception{
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        UserDto userDto = new UserDto(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.getUserById(userDto.getId())).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto)))).thenReturn(userDto);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(userDto);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/travelAgencyBe/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("username")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("email@com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2020-11-07T07:07:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive", Matchers.is("YES")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAdministrator", Matchers.is("NO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfReservations", Matchers.hasSize(0)));
    }

    @Test
    void shouldDeleteUser() throws Exception{
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7), UserStatus.YES, UserStatus.NO, new ArrayList<>());

        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        when(userService.saveUser(user)).thenReturn(user);

        //when&then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/travelAgencyBe/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
