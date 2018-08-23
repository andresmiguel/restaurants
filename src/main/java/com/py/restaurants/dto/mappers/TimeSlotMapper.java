package com.py.restaurants.dto.mappers;

import com.py.restaurants.domain.TimeSlot;
import com.py.restaurants.dto.TimeSlotDto;

public class TimeSlotMapper {

    private TimeSlotMapper() {
    }

    public static TimeSlot fromDto(TimeSlotDto dto) {
        return TimeSlot.builder()
                .openingHour(dto.openingHour)
                .openingMinute(dto.openingMinute)
                .closingHour(dto.closingHour)
                .closingMinute(dto.closingMinute)
                .build();
    }

    public static TimeSlotDto toDto(TimeSlot timeSlot) {
        TimeSlotDto dto = new TimeSlotDto();
        dto.openingHour = timeSlot.getOpeningHour();
        dto.openingMinute = timeSlot.getOpeningMinute();
        dto.closingHour = timeSlot.getClosingHour();
        dto.closingMinute = timeSlot.getClosingMinute();
        return dto;
    }
}
