package com.py.restaurants.domain;

import com.py.restaurants.domain.utils.Checker;
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
        validateHours(openingHour, closingHour);
        validateMinutes(openingMinute, closingMinute);
        this.openingHour = openingHour;
        this.openingMinute = openingMinute;
        this.closingHour = closingHour;
        this.closingMinute = closingMinute;
    }

    private void validateHours(int ...hours) {
        for (int hour : hours) {
            Checker.isValidHour(hour, "Hour is not valid!");
        }
    }

    private void validateMinutes(int ...minutes) {
        for (int minute : minutes) {
            Checker.isValidMinute(minute, "Minute is not valid!");
        }
    }
}
