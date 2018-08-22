package com.py.restaurants.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(Long categoryId) {
        super("Category with id=" + categoryId + " does not exist.");
    }
}
