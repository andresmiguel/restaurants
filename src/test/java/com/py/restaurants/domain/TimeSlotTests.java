package com.py.restaurants.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TimeSlotTests {

    @Test
    public void shouldCreateValidTimeSlot() {
        TimeSlot timeSlot = TimeSlot.builder()
                .openingHour(1)
                .openingMinute(0)
                .closingHour(8)
                .closingMinute(30)
                .build();

        assertThat(timeSlot.getOpeningHour()).isEqualTo(1);
        assertThat(timeSlot.getOpeningMinute()).isEqualTo(0);
        assertThat(timeSlot.getClosingHour()).isEqualTo(8);
        assertThat(timeSlot.getClosingMinute()).isEqualTo(30);
    }

}
