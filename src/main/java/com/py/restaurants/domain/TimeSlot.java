package com.py.restaurants.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class TimeSlot {
    private int openingHour;
    private int openingMinute;
    private int closingHour;
    private int closingMinute;

    @Builder
    public TimeSlot(int openingHour, int openingMinute, int closingHour, int closingMinute) {
        this.openingHour = openingHour;
        this.openingMinute = openingMinute;
        this.closingHour = closingHour;
        this.closingMinute = closingMinute;
    }
}
