package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "TRAVEL_SKY_ID")
    private TravelSky travelSky;

    @OneToOne(mappedBy = "reservation")
    @JoinColumn(name = "COMPLAINT_ID")
    private Complaint complaint;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name = "STATUS")
    @NotNull
    private Status status;

    public Reservation(@NotNull User user, @NotNull Travel travel, @NotNull LocalDateTime creationDate) {
        this.user = user;
        this.travel = travel;
        this.creationDate = creationDate;
        this.status = Status.OPENED;
    }

    public Reservation(@NotNull User user, @NotNull TravelSky travelSky, @NotNull LocalDateTime creationDate) {
        this.user = user;
        this.travelSky = travelSky;
        this.creationDate = creationDate;
        this.status = Status.OPENED;
    }
}
