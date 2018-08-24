package com.py.restaurants.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRestaurantDto extends PageableSearchRestaurantDto {
    public Long categoryId;
    public String namePart;
}
