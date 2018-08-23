package com.py.restaurants.rest;

import com.py.restaurants.domain.Category;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.RestaurantDto;
import com.py.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.dto.mappers.RestaurantMapper;
import com.py.restaurants.exceptions.CategoryNotFoundException;
import com.py.restaurants.exceptions.RestaurantNotFoundException;
import com.py.restaurants.services.RestaurantService;
import org.springframework.web.bind.annotation.*;

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
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAll()
                .map(RestaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurant(@PathVariable Long id) {
        return restaurantService.get(id)
                .map(RestaurantMapper::toDto)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @PostMapping
    public RestaurantDto createRestaurant(@RequestBody RestaurantDto dto) {
        return RestaurantMapper.toDto(restaurantService.add(dto));
    }

    @PutMapping("{id}")
    public RestaurantDto updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto dto) {
        return RestaurantMapper.toDto(restaurantService.update(id, dto));
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return restaurantService.getAllCategories()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        return CategoryMapper.toDto(restaurantService.getCategory(id));
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(@RequestBody CategoryDto dto) {
        Category category = restaurantService.addCategory(dto);
        return CategoryMapper.toDto(category);
    }

    @PutMapping("/categories/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto dto) throws CategoryNotFoundException {
        Category category = restaurantService.updateCategory(id, dto);
        return CategoryMapper.toDto(category);
    }
}
