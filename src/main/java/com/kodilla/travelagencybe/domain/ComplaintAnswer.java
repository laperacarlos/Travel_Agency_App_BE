package com.kodilla.travelagencybe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "COMPLAINTS_ANSWERS")
public class ComplaintAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "COMPLIANT_ID")
    private Complaint complaint;

    @NotNull
    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;
}
