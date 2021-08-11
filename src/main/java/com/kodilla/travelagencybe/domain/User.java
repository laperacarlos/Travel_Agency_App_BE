package com.kodilla.travelagencybe.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
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

    //???? to be considered if necessary
    @Column(name = "ACTIVE")
    @NotNull
    private boolean isActive;

    @Column(name = "ADMINISTRATOR")
    @NotNull
    private boolean isAdministrator;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<Reservation> listOfReservations = new ArrayList<>();

    public User(@NotNull String username, @NotNull String email, @NotNull LocalDateTime creationDate) {
        this.username = username;
        this.email = email;
        this.creationDate = creationDate;
        this.isActive = true;
    }

    public User(@NotNull String username, @NotNull String email, @NotNull LocalDateTime creationDate, @NotNull boolean isAdministrator) {
        this.username = username;
        this.email = email;
        this.creationDate = creationDate;
        this.isAdministrator = isAdministrator;
        this.isActive = true;
    }
}
