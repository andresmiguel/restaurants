package com.py.restaurants.restaurants.dto;

import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public class CompetitorDto {
    public String name;
    public Set<String> commonCategories = new HashSet<>();
}
