package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.UserStatus;
import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class UserTestSuite {

    @Autowired
    UserDao userDao;

    @Autowired
    TimeProvider timeProvider;

    @Test
    public void testCreateUser() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());

        //when
        User savedUser = userDao.save(user);
        Long id = savedUser.getId();

        //then
        assertNotNull(id);
        assertNotEquals(0L, id);

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testReadUser() throws Exception {
        //given
        LocalDateTime creationTime = timeProvider.getTime();
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        User savedUser = userDao.save(user);

        //when
        User userFromDb = userDao.findById(savedUser.getId()).orElseThrow(() -> new UserNotFoundException(savedUser.getId()));

        //then
        assertEquals("username", userFromDb.getUsername());
        assertEquals("email@com", userFromDb.getEmail());
        assertEquals(creationTime, userFromDb.getCreationDate());
        assertEquals(UserStatus.YES, userFromDb.getIsActive());
        assertEquals(UserStatus.NO, userFromDb.getIsAdministrator());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testUpdateUser() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);

        //when
        user.setUsername("newUsername");
        User updatedUser = userDao.save(user);

        //then
        assertEquals("newUsername", updatedUser.getUsername());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testDeleteUser() {
        //given
        User user = new User(null, "username", "email@com", timeProvider.getTime(), UserStatus.YES, UserStatus.NO, new ArrayList<>());
        userDao.save(user);

        //when
        userDao.deleteById(user.getId());
        Optional<User> deletedUser = userDao.findById(user.getId());

        //then
        assertFalse(deletedUser.isPresent());
    }
}
