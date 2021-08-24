package com.kodilla.travelagencybe.repository;

import com.kodilla.travelagencybe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();
}
