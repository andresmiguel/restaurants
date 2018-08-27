package com.py.restaurants.pedidosya.exceptions;

public class PyRestaurantsApiException extends Exception {
    public PyRestaurantsApiException(Throwable cause) {
        super("There was a problem traying to get the restaurants.", cause);
    }
}
