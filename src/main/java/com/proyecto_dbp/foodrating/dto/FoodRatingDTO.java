// FoodRatingDTO.java
package com.proyecto_dbp.foodrating.dto;

import lombok.Data;

@Data
public class FoodRatingDTO {
    private Long ratingId;
    private Integer rating;
    private String comment;
    private Long foodId;
    private Long userId;
}