package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.domain.UserDto;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.mapper.UserMapper;
import com.kodilla.travelagencybe.service.UserService;
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

    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userService.saveNewUser(user));
    }

    @PutMapping(value = "users")
    public UserDto updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        if(userService.getUserById(userDto.getId()).isPresent()) {
            return userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto)));
        } else throw new UserNotFoundException(userDto.getId());

    }

    @PutMapping(value = "users/{id}")
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        userService.deleteUser(user);
    }

    @GetMapping(value = "users")
    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }
}
