package com.proyecto_dbp.food.dto;

import com.proyecto_dbp.food.domain.FoodStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodResponseDto {
    private Long foodId;
    private String name;
    private Double price;
    private FoodStatus status;
    private Double averageRating;
    private LocalDateTime createdDate;
}