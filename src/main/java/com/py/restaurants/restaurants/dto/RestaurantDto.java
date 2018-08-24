package com.py.restaurants.restaurants.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class RestaurantDto {
    public Long id;
    @NotNull(message = "Restaurant name must be present.")
    @NotEmpty(message = "Restaurant name must not be empty.")
    public String name;
    public String description;
    public String phone;
    public double latitude;
    public double longitude;
    public ScheduleDto schedule;
    public Set<CategoryDto> categories;
}
