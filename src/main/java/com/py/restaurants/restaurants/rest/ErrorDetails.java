package com.py.restaurants.restaurants.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private int status;
    private String error;
}