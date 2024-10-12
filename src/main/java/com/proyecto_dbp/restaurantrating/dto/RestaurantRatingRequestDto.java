package com.proyecto_dbp.restaurantrating.dto;

import lombok.Data;

@Data
public class RestaurantRatingRequestDto {
    private Long restaurantId;
    private Long userId;
    private Integer rating;
    private String comment;
}