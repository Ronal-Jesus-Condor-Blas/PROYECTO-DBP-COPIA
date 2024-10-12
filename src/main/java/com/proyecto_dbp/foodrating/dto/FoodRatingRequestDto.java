package com.proyecto_dbp.foodrating.dto;

import lombok.Data;

@Data
public class FoodRatingRequestDto {
    private Long foodId;
    private Long userId;
    private Integer rating;
    private String comment;
}