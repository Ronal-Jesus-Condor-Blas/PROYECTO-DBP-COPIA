package com.proyecto_dbp.restaurant.dto;

import com.proyecto_dbp.restaurant.domain.RestaurantStatus;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class RestaurantRequestDto {
    private String name;
    private Float latitude;
    private Float longitude;
    private RestaurantStatus status;
    private String email;
}
