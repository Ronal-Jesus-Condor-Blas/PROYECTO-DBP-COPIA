// RestaurantRatingDTO.java
package com.proyecto_dbp.restaurantrating.dto;

import lombok.Data;

@Data
public class RestaurantRatingDTO {
    private Long ratingId;
    private Integer rating;
    private String comment;
    private Long restaurantId;
    private Long userId;
}