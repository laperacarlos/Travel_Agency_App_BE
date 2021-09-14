package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.enums.TravelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRAVEL_ID")
    private Travel travel;

    @Column(name = "TRAVEL_TYPE")
    @NotNull
    private TravelType travelType;

    @Column(name = "HOTEL_STANDARD")
    @NotNull
    private HotelStandard hotelStandard;

    @Column(name = "MEAL_STANDARD")
    @NotNull
    private MealStandard mealStandard;

    @OneToOne(mappedBy = "reservation")
    private Complaint complaint;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name = "STATUS")
    @NotNull
    private Status status;
}
