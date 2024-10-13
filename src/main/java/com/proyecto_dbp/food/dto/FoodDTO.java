// FoodDTO.java
package com.proyecto_dbp.food.dto;

import lombok.Data;

@Data
public class FoodDTO {
    private Long foodId;
    private String name;
    private Double price;
    private Double averageRating;
    private String status;
}