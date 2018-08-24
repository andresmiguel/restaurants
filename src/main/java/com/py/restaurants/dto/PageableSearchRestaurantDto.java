package com.py.restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableSearchRestaurantDto {
    public Integer page;
    public Integer pageSize;
}
