package com.py.restaurants.restaurants.exceptions;

public class RestaurantSearchException extends Exception {
    public RestaurantSearchException() {
        super("You must provide at least one criteria to search for.");
    }
}
