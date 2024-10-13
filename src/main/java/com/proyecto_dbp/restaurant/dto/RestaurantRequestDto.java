package com.proyecto_dbp.restaurant.dto;

import com.proyecto_dbp.restaurant.domain.RestaurantStatus;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class RestaurantRequestDto {
    private String name;
    private String location;
    private RestaurantStatus status;
    //Agregar email para recibir confirmaciones de "foodrating"
    private String email;
}