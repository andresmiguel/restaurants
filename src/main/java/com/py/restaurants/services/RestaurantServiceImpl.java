package com.py.restaurants.services;

import com.py.restaurants.domain.Category;
import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.RestaurantDto;
import com.py.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public Optional<Restaurant> get(Long id) {
        return Optional.ofNullable(restaurantRepository.findOne(id));
    }

    @Override
    public Stream<Restaurant> getAll() {
        return StreamSupport.stream(restaurantRepository.findAll().spliterator(),false);
    }

    @Override
    public Stream<Restaurant> getAllWithSimilarName(String namePart) {
        return StreamSupport.stream(
                restaurantRepository.findByNameContaining(namePart).spliterator(),
                false);
    }

    @Override
    public Stream<Restaurant> getByCategory(Long categoryId) {
        return StreamSupport.stream(
                restaurantRepository.findByCategoriesId(categoryId).spliterator(),
                false);
    }

    @Override
    public Restaurant add(RestaurantDto dto) {
        return null;
    }

    @Override
    public Restaurant update(Long id, RestaurantDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        restaurantRepository.delete(id);
    }

    @Override
    public Stream<Category> getAllCategories() {
        return StreamSupport.stream(
                categoryRepository.findAll().spliterator(),
                false);
    }

    @Override
    public Category addCategory(CategoryDto dto) {
        Category category = CategoryMapper.fromDto(dto);
        return categoryRepository.save(category);
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
    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
}
