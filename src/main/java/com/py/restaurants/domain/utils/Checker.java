package com.py.restaurants.domain.utils;

public class Checker {

    private Checker() { }

    public static <T extends Throwable> void isNotBlank(String value, String errorMessage) throws T {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(errorMessage);
        }
    }

    public static <T extends Throwable> void isValidHour(int hour, String errorMessage) throws T {
        if (hour > 23 || hour < 0) {
            throw new IllegalStateException(errorMessage);
        }
    }

    public static <T extends Throwable> void isValidMinute(int minute, String errorMessage) throws T {
        if (minute > 59 || minute < 0) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
