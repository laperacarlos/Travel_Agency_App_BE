package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.HotelStandard;
import com.kodilla.travelagencybe.enums.MealStandard;
import com.kodilla.travelagencybe.enums.Status;
import com.kodilla.travelagencybe.enums.TravelType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "TRAVELS")
public class Travel {

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

    @Column(name = "TRAVEL_TYPE")
    @NotNull
    private TravelType travelType;

    @Column(name = "HOTEL_STANDARD")
    @NotNull
    private HotelStandard hotelStandard;

    @Column(name = "MEAL_STANDARD")
    @NotNull
    private MealStandard mealStandard;

    @Column(name = "STATUS")
    @NotNull
    private Status status;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "travel",
            fetch = FetchType.EAGER
    )
    private List<Reservation> listOfReservations = new ArrayList<>();

    public Travel(@NotNull String origin, @NotNull String destination, @NotNull LocalDateTime departureDate,
                  @NotNull LocalDateTime returnDate, @NotNull TravelType travelType, @NotNull HotelStandard hotelStandard,
                  @NotNull MealStandard mealStandard, @NotNull LocalDateTime creationDate) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.travelType = travelType;
        this.hotelStandard = hotelStandard;
        this.mealStandard = mealStandard;
        this.creationDate = creationDate;
        this.status = Status.OPENED;
    }
}
