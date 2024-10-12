package com.proyecto_dbp.food.dto;

import com.proyecto_dbp.food.domain.FoodStatus;
import lombok.Data;

@Data
public class FoodRequestDto {
    private String name;
    private Double price;
    private FoodStatus status;
}