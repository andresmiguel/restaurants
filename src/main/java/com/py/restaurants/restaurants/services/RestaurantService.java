package com.py.restaurants.restaurants.services;

import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.domain.Restaurant;
import com.py.restaurants.restaurants.dto.CategoryDto;
import com.py.restaurants.restaurants.dto.PageableSearchRestaurantDto;
import com.py.restaurants.restaurants.dto.RestaurantDto;
import com.py.restaurants.restaurants.dto.SearchRestaurantDto;
import com.py.restaurants.restaurants.exceptions.*;

import java.util.stream.Stream;

public interface RestaurantService {
    Restaurant get(Long id) throws RestaurantNotFoundException;
    Stream<Restaurant> getAll(PageableSearchRestaurantDto pageableSearchRestaurantDto);
    Restaurant add(RestaurantDto dto) throws DuplicateRestaurantNameException;
    Restaurant update(Long id, RestaurantDto dto) throws RestaurantNotFoundException;
    void delete(Long id) throws RestaurantNotFoundException;
    Stream<Restaurant> search(SearchRestaurantDto searchRestaurantDto) throws RestaurantSearchException;

    Category getCategory(Long id) throws CategoryNotFoundException;
    Stream<Category> getAllCategories();
    Category addCategory(CategoryDto dto) throws DuplicateCategoryNameException;
    Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException;
    void deleteCategory(Long id) throws CategoryNotFoundException;
}
