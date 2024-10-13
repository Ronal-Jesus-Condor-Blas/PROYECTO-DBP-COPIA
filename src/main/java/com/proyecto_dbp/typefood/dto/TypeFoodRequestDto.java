package com.proyecto_dbp.typefood.dto;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class TypeFoodRequestDto {
    private String type;
    private String description;
    private Long restaurantId;


    public Long getRestaurantId() {
        return restaurantId;
    }



}