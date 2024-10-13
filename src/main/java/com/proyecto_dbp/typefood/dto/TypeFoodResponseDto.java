package com.proyecto_dbp.typefood.dto;

import lombok.Data;

@Data
public class TypeFoodResponseDto {
    private Long typeFoodId;
    private String type;
    private String description;
}