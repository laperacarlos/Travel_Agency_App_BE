package com.kodilla.travelagencybe.repository;

import com.kodilla.travelagencybe.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReservationDao extends JpaRepository<Reservation, Long> {
}
