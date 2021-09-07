package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
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
@Entity(name = "TRAVELS_SKY")
public class TravelSky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORIGIN")
    @NotNull
    private String origin;

    @Column(name = "DESTINATION")
    @NotNull
    private String destination;

    @Column(name = "DEPARTURE_DATE")
    @NotNull
    private LocalDateTime departureDate;

    @Column(name = "RETURN_DATE")
    @NotNull
    private LocalDateTime returnDate;

    @Column(name = "STATUS")
    @NotNull
    private Status status;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "travelSky",
            fetch = FetchType.EAGER
    )
    private List<Reservation> listOfReservations = new ArrayList<>();
}
