package com.py.restaurants.services;

import com.py.restaurants.domain.Category;
import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.RestaurantDto;
import com.py.restaurants.exceptions.CategoryNotFoundException;
import com.py.restaurants.exceptions.DuplicateCategoryNameException;
import com.py.restaurants.exceptions.DuplicateRestaurantNameException;

import java.util.Optional;
import java.util.stream.Stream;

public interface RestaurantService {
    Optional<Restaurant> get(Long id);
    Stream<Restaurant> getAll();
    Stream<Restaurant> getAllWithSimilarName(String namePart);
    Stream<Restaurant> getByCategory(Long categoryId);
    Restaurant add(RestaurantDto dto) throws DuplicateRestaurantNameException;
    Restaurant update(Long id, RestaurantDto dto) throws DuplicateRestaurantNameException;
    void delete(Long id);

    Category getCategory(Long id);
    Stream<Category> getAllCategories();
    Category addCategory(CategoryDto dto) throws DuplicateCategoryNameException;
    Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException, DuplicateCategoryNameException;
    void deleteCategory(Long id);
}
