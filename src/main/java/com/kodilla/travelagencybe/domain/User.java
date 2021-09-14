package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    @NotNull
    private String username;

    @Column(name = "EMAIL")
    @NotNull
    private String email;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name = "ACTIVE")
    @NotNull
    private UserStatus isActive;

    @Column(name = "ADMINISTRATOR")
    @NotNull
    private UserStatus isAdministrator;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<Reservation> listOfReservations = new ArrayList<>();
}
