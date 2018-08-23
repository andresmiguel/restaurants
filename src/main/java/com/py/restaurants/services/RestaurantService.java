package com.py.restaurants.services;

import com.py.restaurants.domain.Category;
import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.RestaurantDto;
import com.py.restaurants.exceptions.CategoryNotFoundException;

import java.util.Optional;
import java.util.stream.Stream;

public interface RestaurantService {
    Optional<Restaurant> get(Long id);
    Stream<Restaurant> getAll();
    Stream<Restaurant> getAllWithSimilarName(String namePart);
    Stream<Restaurant> getByCategory(Long categoryId);
    Restaurant add(RestaurantDto dto);
    Restaurant update(Long id, RestaurantDto dto);
    void delete(Long id);

    Category getCategory(Long id);
    Stream<Category> getAllCategories();
    Category addCategory(CategoryDto dto);
    Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException;
    void deleteCategory(Long id);
}
