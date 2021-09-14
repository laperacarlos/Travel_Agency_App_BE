package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.User;
import com.kodilla.travelagencybe.enums.UserStatus;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final TimeProvider timeProvider;

    public User saveNewUser(final User user){
        user.setCreationDate(timeProvider.getTime());
        return userDao.save(user);
    }

    public void deleteUser(final User user) {
        user.setIsActive(UserStatus.NO);
        userDao.save(user);
    }

    public User saveUser(final User user){
        return userDao.save(user);
    }

    public Optional<User> getUserById(final Long id){
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
