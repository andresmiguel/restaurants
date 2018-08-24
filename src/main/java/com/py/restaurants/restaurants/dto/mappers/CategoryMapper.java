package com.py.restaurants.restaurants.dto.mappers;

import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.dto.CategoryDto;

public class CategoryMapper {

    private CategoryMapper() {}

    public static Category fromDto(CategoryDto dto) {
        Category category = new Category(dto.name);
        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.name = category.getName();
        return dto;
    }

    public static void copyFromDto(Category category, CategoryDto dto) {
        category.setName(dto.name);
    }
}
