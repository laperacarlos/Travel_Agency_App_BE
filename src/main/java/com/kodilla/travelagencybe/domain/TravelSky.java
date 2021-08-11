package com.kodilla.travelagencybe.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity(name = "TRAVELS_SKY")
public class TravelSky extends Travel {

    //tutaj tylko jedna rezerwacja do jednej konkretnej podórzy która powstanie z wyszukiwarki
}
