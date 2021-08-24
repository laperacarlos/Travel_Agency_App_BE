package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User saveUser(final User user){
        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
