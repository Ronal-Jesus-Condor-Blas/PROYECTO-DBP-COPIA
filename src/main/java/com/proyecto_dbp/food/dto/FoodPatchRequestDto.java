package com.proyecto_dbp.food.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.proyecto_dbp.food.domain.FoodStatus;
import com.proyecto_dbp.food.domain.FoodStatusDeserializer;
import lombok.Data;

@Data
public class FoodPatchRequestDto {
    private String name;
    private Double price;

    private String status;

    private Long restaurantId;
}