package com.py.restaurants.pedidosya.api.dto;

import com.py.restaurants.pedidosya.api.PyRestaurant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PyRestaurantMapper {

    public static PyRestaurant fromDto(PyRestaurantDto dto) {
        Set<String> categories = Arrays.stream(dto.allCategories.split(","))
                    .collect(Collectors.toSet());

        return PyRestaurant.builder()
                .name(dto.name)
                .categories(categories)
                .build();
    }
}
