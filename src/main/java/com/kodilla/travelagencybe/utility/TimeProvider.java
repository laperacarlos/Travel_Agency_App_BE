package com.kodilla.travelagencybe.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProvider {

    public LocalDateTime getTime() {
        return LocalDateTime.now().withNano(0);
    }
}
