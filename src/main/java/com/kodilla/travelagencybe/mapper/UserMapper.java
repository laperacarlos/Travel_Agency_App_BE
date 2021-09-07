package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getCreationDate(),
                userDto.isActive(),
                userDto.isAdministrator(),
                userDto.getListOfReservations()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreationDate(),
                user.isActive(),
                user.isAdministrator(),
                user.getListOfReservations()
        );
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
