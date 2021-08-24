package com.kodilla.travelagencybe.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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

    public ComplaintAnswer(@NotNull Complaint complaint, @NotNull String answer, @NotNull LocalDateTime creationDate) {
        this.complaint = complaint;
        this.answer = answer;
        this.creationDate = creationDate;
    }
}
