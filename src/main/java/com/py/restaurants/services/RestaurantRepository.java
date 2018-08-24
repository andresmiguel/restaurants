package com.py.restaurants.services;

import com.py.restaurants.domain.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingAndDeletedFalse(String namePart);
    List<Restaurant> findByCategoriesIdAndDeletedFalse(Long id, Pageable pageable);
    List<Restaurant> findByDeletedFalse(Pageable pageable);
}
