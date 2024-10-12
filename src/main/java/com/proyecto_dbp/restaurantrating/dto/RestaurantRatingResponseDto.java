package com.proyecto_dbp.restaurantrating.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantRatingResponseDto {
    private Long restaurantRatingId;
    private Long restaurantId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime ratingDate;
}