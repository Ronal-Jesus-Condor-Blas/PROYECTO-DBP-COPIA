package com.proyecto_dbp.typefood.dto;


import lombok.Data;

@Data
public class TypeFoodDTO {
    private Long typeFoodId;
    private String name;
    private String description;
    private Long restaurantID;
}