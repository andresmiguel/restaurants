package com.py.restaurants.restaurants.rest;

import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.dto.CategoryDto;
import com.py.restaurants.restaurants.dto.PageableSearchRestaurantDto;
import com.py.restaurants.restaurants.dto.RestaurantDto;
import com.py.restaurants.restaurants.dto.SearchRestaurantDto;
import com.py.restaurants.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.restaurants.dto.mappers.RestaurantMapper;
import com.py.restaurants.restaurants.exceptions.*;
import com.py.restaurants.restaurants.services.RestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDto> getAllRestaurants(PageableSearchRestaurantDto pageableSearchRestaurantDto) {
        return restaurantService.getAll(pageableSearchRestaurantDto)
                .map(RestaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<RestaurantDto> search(SearchRestaurantDto searchRestaurantDto) throws RestaurantSearchException {
        return restaurantService.search(searchRestaurantDto)
                .map(RestaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurant(@PathVariable Long id) throws RestaurantNotFoundException {
        return RestaurantMapper.toDto(restaurantService.get(id));
    }

    @PostMapping
    public RestaurantDto createRestaurant(@Valid @RequestBody RestaurantDto dto) throws DuplicateRestaurantNameException {
        return RestaurantMapper.toDto(restaurantService.add(dto));
    }

    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantDto dto) throws RestaurantNotFoundException {
        return RestaurantMapper.toDto(restaurantService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) throws RestaurantNotFoundException {
        restaurantService.delete(id);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return restaurantService.getAllCategories()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return CategoryMapper.toDto(restaurantService.getCategory(id));
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto dto) throws DuplicateCategoryNameException {
        Category category = restaurantService.addCategory(dto);
        return CategoryMapper.toDto(category);
    }

    @PutMapping("/categories/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) throws CategoryNotFoundException {
        Category category = restaurantService.updateCategory(id, dto);
        return CategoryMapper.toDto(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        restaurantService.deleteCategory(id);
    }
}
