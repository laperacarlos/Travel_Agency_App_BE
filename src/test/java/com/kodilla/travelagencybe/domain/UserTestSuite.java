package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.exception.UserNotFoundException;
import com.kodilla.travelagencybe.repository.UserDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
        User user = new User("username", "email@com", timeProvider.getTime());

        //when
        User savedUser = userDao.save(user);

        //then
        assertNotEquals(0L, savedUser.getId());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testReadUser() throws Exception {
        //given
        LocalDateTime creationTime = timeProvider.getTime();
        User user = new User("username", "email@com", creationTime);
        User savedUser = userDao.save(user);

        //when
        User userFromDb = userDao.findById(savedUser.getId()).orElseThrow(() -> new UserNotFoundException(savedUser.getId()));

        //then
        assertEquals("username", userFromDb.getUsername());
        assertEquals("email@com", userFromDb.getEmail());
        assertEquals(creationTime, userFromDb.getCreationDate());
        assertTrue(userFromDb.isActive());
        assertFalse(userFromDb.isAdministrator());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testUpdateUser() {
        //given
        User user = new User("username", "email@com", timeProvider.getTime());
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
        User user = new User("username", "email@com", timeProvider.getTime());
        userDao.save(user);

        //when
        userDao.deleteById(user.getId());
        Optional<User> deletedUser = userDao.findById(user.getId());

        //then
        assertFalse(deletedUser.isPresent());
    }
}
