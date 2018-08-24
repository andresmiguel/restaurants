package com.py.restaurants.services;

import com.py.restaurants.domain.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingAndDeletedFalse(String namePart, Pageable pageable);
    List<Restaurant> findByCategoriesIdAndDeletedFalse(Long categoryId, Pageable pageable);
    List<Restaurant> findByDeletedFalse(Pageable pageable);
    List<Restaurant> findByCategoriesIdAndNameContainingAndDeletedFalse(Long id, String namePart, Pageable pageable);
}
