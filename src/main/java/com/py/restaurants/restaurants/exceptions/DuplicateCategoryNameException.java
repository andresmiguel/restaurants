package com.py.restaurants.restaurants.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateCategoryNameException extends Exception {
    public DuplicateCategoryNameException() {
        super("Category name already exists.");
    }
}
