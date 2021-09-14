package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.enums.UserStatus;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private TimeProvider timeProvider;

    @Test
    void createUser() {
        //given
        LocalDateTime dateTime = timeProvider.getTime();

        User user = new User(null, "username", "email@com", dateTime, UserStatus.YES, UserStatus.NO, new ArrayList<>());
        User savedUser = new User(1L, "username", "email@com", dateTime, UserStatus.YES, UserStatus.NO, new ArrayList<>());

        when(timeProvider.getTime()).thenReturn(dateTime);
        when(userDao.save(user)).thenReturn(savedUser);

        //when
        User newUser = userService.saveNewUser(user);

        //then
        assertEquals(1L, newUser.getId());
    }

    @Test
    void getUserById() throws UserNotFoundException {
        //given
        User savedUser = new User(1L, "username", "email@com", LocalDateTime.now(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        Optional<User> optUser = Optional.of(savedUser);

        when(userDao.findById(savedUser.getId())).thenReturn(optUser);

        //when
        User retrievedUser = userService.getUserById(savedUser.getId()).orElseThrow(() -> new UserNotFoundException(savedUser.getId()));

        //then
        assertEquals(1L, retrievedUser.getId());
    }

    @Test
    void getAllUsers() {
        //given
        List<User> userList = List.of(new User(1L, "username", "email@com", LocalDateTime.now(), UserStatus.YES, UserStatus.NO, new ArrayList<>()));

        when(userDao.findAll()).thenReturn(userList);

        //when
        List<User> list = userService.getAllUsers();

        //then
        assertEquals(1, list.size());
    }
}
