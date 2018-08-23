package com.py.restaurants.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(Long categoryId) {
        super("Category with id=" + categoryId + " does not exist.");
    }
}
