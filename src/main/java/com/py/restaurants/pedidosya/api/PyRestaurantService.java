package com.py.restaurants.pedidosya.api;

import com.py.restaurants.pedidosya.exceptions.PyRestaurantsApiException;
import org.springframework.data.geo.Point;

import java.util.List;

public interface PyRestaurantService {

    List<PyRestaurant> findByCountryAndLocation(int country, Point point) throws PyRestaurantsApiException;
}
