package com.kodilla.travelagencybe.domain;

import com.kodilla.travelagencybe.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "COMPLAINTS")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name = "CLOSING_DATE")
    private LocalDateTime closingDate;

    @OneToOne(mappedBy = "complaint")
    private ComplaintAnswer complaintAnswer;

    @Column(name = "STATUS")
    @NotNull
    private Status status;
}
