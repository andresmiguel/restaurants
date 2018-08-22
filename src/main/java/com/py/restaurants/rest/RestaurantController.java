package com.py.restaurants.rest;

import com.py.restaurants.domain.Category;
import com.py.restaurants.dto.CategoryDto;
import com.py.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.exceptions.CategoryNotFoundException;
import com.py.restaurants.services.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/categories")
    public Object getAllCategories() {
        return restaurantService.getAllCategories()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/categories")
    public Object createCategory(@RequestBody CategoryDto dto) {
        Category category = restaurantService.addCategory(dto);
        return CategoryMapper.toDto(category);
    }

    @PutMapping("/categories/{id}")
    public Object updateCategory(@PathVariable Long id, @RequestBody CategoryDto dto) throws CategoryNotFoundException {
        Category category = restaurantService.updateCategory(id, dto);
        return CategoryMapper.toDto(category);
    }
}
