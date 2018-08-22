package com.py.restaurants.services;

import com.py.restaurants.domain.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

    List<Restaurant> findByNameContaining(String namePart);
    List<Restaurant> findByCategoriesId(Long id);
}
