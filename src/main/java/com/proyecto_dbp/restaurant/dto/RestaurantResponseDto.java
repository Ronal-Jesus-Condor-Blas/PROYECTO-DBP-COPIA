package com.proyecto_dbp.restaurant.dto;

import com.proyecto_dbp.restaurant.domain.RestaurantStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantResponseDto {
    private Long restaurantId;
    private String name;
    private String location;
    private RestaurantStatus status;
    private Double averageRating;
    private LocalDateTime createdDate;
}