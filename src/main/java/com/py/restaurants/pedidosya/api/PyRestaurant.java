package com.py.restaurants.pedidosya.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class PyRestaurant {
    private String name;
    private Set<String> categories;
}
