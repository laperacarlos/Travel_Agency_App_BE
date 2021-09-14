package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.domain.UserDto;
import com.kodilla.travelagencybe.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTestSuite {

    @Autowired
    UserMapper userMapper;

    @Test
    void testMapToUser() {
        //given
        UserDto userDto = new UserDto(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7),
                UserStatus.YES, UserStatus.NO, new ArrayList<>());

        //when
        User user = userMapper.mapToUser(userDto);

        //then
        assertEquals(1L, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("email@com", user.getEmail());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), user.getCreationDate());
        assertEquals(UserStatus.YES, user.getIsActive());
        assertEquals(UserStatus.NO, user.getIsAdministrator());
        assertEquals(0, user.getListOfReservations().size());
    }

    @Test
    void testMapToUserDto() {
        //given
        User user = new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7),
                UserStatus.YES, UserStatus.NO, new ArrayList<>());

        //when
        UserDto userDto = userMapper.mapToUserDto(user);

        //then
        assertEquals(1L, userDto.getId());
        assertEquals("username", userDto.getUsername());
        assertEquals("email@com", userDto.getEmail());
        assertEquals(LocalDateTime.of(2020, 11, 7, 7, 7), userDto.getCreationDate());
        assertEquals(UserStatus.YES, userDto.getIsActive());
        assertEquals(UserStatus.NO, userDto.getIsAdministrator());
        assertEquals(0, userDto.getListOfReservations().size());
    }

    @Test
    void testMapToUserDtoList() {
        //given
        List<User> userList = List.of(new User(1L, "username", "email@com", LocalDateTime.of(2020, 11, 7, 7, 7),
                UserStatus.YES, UserStatus.NO, new ArrayList<>()));

        //when
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);

        //then
        assertEquals(1, userDtoList.size());
    }
}
