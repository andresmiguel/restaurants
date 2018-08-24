package com.py.restaurants.services;

import com.py.restaurants.domain.Category;
import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.RestaurantDto;
import com.py.restaurants.dto.SearchRestaurantDto;
import com.py.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.dto.mappers.RestaurantMapper;
import com.py.restaurants.exceptions.CategoryNotFoundException;
import com.py.restaurants.exceptions.DuplicateCategoryNameException;
import com.py.restaurants.exceptions.DuplicateRestaurantNameException;
import com.py.restaurants.exceptions.RestaurantNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 CategoryRepository categoryRepository) {
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Restaurant get(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null || restaurant.isDeleted()) {
            throw new RestaurantNotFoundException(id);
        }

        return restaurant;
    }

    @Override
    public Stream<Restaurant> getAll(SearchRestaurantDto searchRestaurantDto) {
        List<Restaurant> restaurants;
        if (searchRestaurantDto.categoryId != null) {
            restaurants = restaurantRepository.findByCategoriesIdAndDeletedFalse(searchRestaurantDto.categoryId);
        } else {
            restaurants = restaurantRepository.findByDeletedFalse();
        }

        return StreamSupport.stream(restaurants.spliterator(),false);
    }

    @Override
    public Stream<Restaurant> getAllWithSimilarName(String namePart) {
        return StreamSupport.stream(
                restaurantRepository.findByNameContainingAndDeletedFalse(namePart).spliterator(),
                false);
    }

    @Override
    public Stream<Restaurant> getByCategory(Long categoryId) {
        return StreamSupport.stream(
                restaurantRepository.findByCategoriesIdAndDeletedFalse(categoryId).spliterator(),
                false);
    }

    @Override
    public Restaurant add(RestaurantDto dto) throws DuplicateRestaurantNameException {
        Restaurant restaurant = RestaurantMapper.fromDto(dto);
        setRestaurantCategories(dto, restaurant);

        try {
            restaurant = restaurantRepository.save(restaurant);
        } catch (DataIntegrityViolationException div) {
            throw new DuplicateRestaurantNameException();
        }

        return restaurant;
    }

    @Override
    public Restaurant update(Long id, RestaurantDto dto) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        RestaurantMapper.copyFromDto(restaurant, dto);
        restaurant.getCategories().clear();
        setRestaurantCategories(dto, restaurant);

        return restaurantRepository.save(restaurant);
    }

    private void setRestaurantCategories(RestaurantDto dto, Restaurant restaurant) {
        Set<Category> categories = dto.categories
                .stream()
                .map(c -> c.id)
                .map(categoryRepository::findOne)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        restaurant.getCategories().addAll(categories);
    }

    @Override
    public void delete(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        restaurant.setDeleted(true);
        restaurantRepository.save(restaurant);
    }

    @Override
    public Category getCategory(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findOne(id);

        if (category == null) {
            throw new CategoryNotFoundException(id);
        }

        return category;
    }

    @Override
    public Stream<Category> getAllCategories() {
        return StreamSupport.stream(
                categoryRepository.findAll().spliterator(),
                false);
    }

    @Override
    public Category addCategory(CategoryDto dto) throws DuplicateCategoryNameException {

        Category category = CategoryMapper.fromDto(dto);
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException div) {
            throw new DuplicateCategoryNameException();
        }

        return category;
    }

    @Override
    public Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException {
        Category category = categoryRepository.findOne(id);

        if (category == null) {
            throw new CategoryNotFoundException(id);
        }

        CategoryMapper.copyFromDto(category, dto);

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        try {
            categoryRepository.delete(id);
        } catch (EmptyResultDataAccessException erda) {
            throw new CategoryNotFoundException(id);
        }
    }
}
