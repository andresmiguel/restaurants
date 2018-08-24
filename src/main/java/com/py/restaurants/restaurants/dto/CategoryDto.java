package com.py.restaurants.restaurants.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class CategoryDto {
    public Long id;
    @NotNull(message = "Category name must be present.")
    @NotEmpty(message = "Category name must not be empty.")
    public String name;
}

