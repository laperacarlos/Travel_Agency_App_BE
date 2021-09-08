package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.domain.UserDto;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.mapper.UserMapper;
import com.kodilla.travelagencybe.service.UserService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user.setCreationDate(timeProvider.getTime());
        return userService.saveUser(user);
    }

    @PutMapping(value = "users")
    public void updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        if(userService.getUserById(userDto.getId()).isPresent()) {
            userService.saveUser(userMapper.mapToUser(userDto));
        } else throw new UserNotFoundException(userDto.getId());

    }

    @PutMapping(value = "users/{id}")
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        userService.saveUser(user);
    }

    @GetMapping(value = "users")
    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }
}
