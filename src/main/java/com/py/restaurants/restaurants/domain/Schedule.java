package com.py.restaurants.restaurants.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Schedule {

    @Id
    @SequenceGenerator(name = "scheduleRangeGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduleRangeGen")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "openingHour", column = @Column(name = "weekdays_opening_hour")),
            @AttributeOverride(name = "openingMinute", column = @Column(name = "weekdays_opening_minute")),
            @AttributeOverride(name = "closingHour", column = @Column(name = "weekdays_closing_hour")),
            @AttributeOverride(name = "closingMinute", column = @Column(name = "weekdays_closing_minute"))
    })
    private TimeSlot weekDays;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "openingHour", column = @Column(name = "weekenddays_opening_hour")),
            @AttributeOverride(name = "openingMinute", column = @Column(name = "weekenddays_opening_minute")),
            @AttributeOverride(name = "closingHour", column = @Column(name = "weekenddays_closing_hour")),
            @AttributeOverride(name = "closingMinute", column = @Column(name = "weekenddays_closing_minute"))
    })
    private TimeSlot weekendDays;

    @Builder
    public Schedule(TimeSlot weekDays, TimeSlot weekendDays) {
        this.weekDays = weekDays;
        this.weekendDays = weekendDays;
    }
}
