package com.proyecto_dbp.typefood.dto;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class TypeFoodResponseDto {
    private Long typeFoodId;
    private String type;
    private String description;

}