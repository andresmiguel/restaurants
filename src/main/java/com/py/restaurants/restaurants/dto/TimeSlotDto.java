package com.py.restaurants.restaurants.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class TimeSlotDto {
    @Min(value = 0, message = "Opening hour must not be lesser than 0.")
    @Max(value = 23, message = "Opening hour must not be greater than 23.")
    public int openingHour;

    @Min(value = 0, message = "Opening minutes must not be lesser than 0.")
    @Max(value = 59, message = "Opening minutes must not be greater than 59.")
    public int openingMinute;

    @Min(value = 0, message = "Closing hour must not be lesser than 0.")
    @Max(value = 23, message = "Closing hour must not be greater than 23.")
    public int closingHour;

    @Min(value = 0, message = "Closing minutes must not be lesser than 0.")
    @Max(value = 59, message = "Closing minutes must not be greater than 59.")
    public int closingMinute;
}
