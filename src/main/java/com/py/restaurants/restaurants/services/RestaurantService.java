package com.py.restaurants.restaurants.services;

import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.domain.Restaurant;
import com.py.restaurants.restaurants.dto.*;
import com.py.restaurants.restaurants.exceptions.*;

import java.util.List;
import java.util.stream.Stream;

public interface RestaurantService {
    Restaurant get(Long id) throws RestaurantNotFoundException;
    Stream<Restaurant> getAll(PageableSearchRestaurantDto pageableSearchRestaurantDto);
    Restaurant add(RestaurantDto dto) throws DuplicateRestaurantNameException;
    Restaurant update(Long id, RestaurantDto dto) throws RestaurantNotFoundException;
    void delete(Long id) throws RestaurantNotFoundException;
    Stream<Restaurant> search(SearchRestaurantDto searchRestaurantDto) throws RestaurantSearchException;
    List<CompetitorDto> findCompetitors(Long id) throws RestaurantNotFoundException, ExternalServiceException;

    Category getCategory(Long id) throws CategoryNotFoundException;
    Stream<Category> getAllCategories();
    Category addCategory(CategoryDto dto) throws DuplicateCategoryNameException;
    Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException;
    void deleteCategory(Long id) throws CategoryNotFoundException;
}
