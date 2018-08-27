package com.py.restaurants.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static  {
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {}

    public static <T> T toObject(String jsonStr, Class<T> clazz) throws IOException {
        return objectMapper.readValue(jsonStr, clazz);
    }

    public static String toString(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}
