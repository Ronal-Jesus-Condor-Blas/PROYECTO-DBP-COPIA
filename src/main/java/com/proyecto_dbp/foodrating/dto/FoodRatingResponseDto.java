package com.proyecto_dbp.foodrating.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodRatingResponseDto {
    private Long foodRatingId;
    private Long foodId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime ratingDate;
}