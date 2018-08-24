package com.py.restaurants.restaurants.dto.mappers;

import com.py.restaurants.restaurants.domain.Schedule;
import com.py.restaurants.restaurants.dto.ScheduleDto;

public class ScheduleMapper {

    private ScheduleMapper() {
    }

    public static Schedule fromDto(ScheduleDto dto) {
        return Schedule.builder()
                .weekDays(TimeSlotMapper.fromDto(dto.weekDays))
                .weekendDays(TimeSlotMapper.fromDto(dto.weekendDays))
                .build();
    }

    public static ScheduleDto toDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();
        dto.weekDays = TimeSlotMapper.toDto(schedule.getWeekDays());
        dto.weekendDays = TimeSlotMapper.toDto(schedule.getWeekendDays());
        return dto;
    }
}
