package com.py.restaurants.dto.mappers;

import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.RestaurantDto;

import java.util.stream.Collectors;

public class RestaurantMapper {

    private RestaurantMapper() {}

    public static Restaurant fromDto(RestaurantDto dto) {

        return Restaurant.builder()
                .name(dto.name)
                .description(dto.description)
                .latitude(dto.latitude)
                .longitude(dto.longitude)
                .phone(dto.phone)
                .schedule(ScheduleMapper.fromDto(dto.schedule))
                .build();
    }

    public static RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.description = restaurant.getDescription();
        dto.id = restaurant.getId();
        dto.latitude = restaurant.getLatitude();
        dto.longitude = restaurant.getLongitude();
        dto.name = restaurant.getName();
        dto.phone = restaurant.getPhone();
        dto.schedule = ScheduleMapper.toDto(restaurant.getSchedule());
        dto.categories = restaurant.getCategories()
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toSet());

        return dto;
    }

    public static void copyFromDto(Restaurant restaurant, RestaurantDto dto) {
        restaurant.setName(dto.name);
        restaurant.setDescription(dto.description);
        restaurant.setLatitude(dto.latitude);
        restaurant.setLongitude(dto.longitude);
        restaurant.setPhone(dto.phone);
        restaurant.setSchedule(ScheduleMapper.fromDto(dto.schedule));
    }
}
