package com.proyecto_dbp.typefood.dto;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import lombok.Data;

import java.util.Set;

@Data
public class TypeFoodRequestDto {
    private String type;
    private String description;

    private Set<Restaurant> restaurants;


}