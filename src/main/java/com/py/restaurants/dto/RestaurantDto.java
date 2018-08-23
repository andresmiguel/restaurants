package com.py.restaurants.dto;

import java.util.Set;

public class RestaurantDto {
    public Long id;
    public String name;
    public String description;
    public String phone;
    public double latitude;
    public double longitude;
    public ScheduleDto schedule;
    public Set<CategoryDto> categories;
}
