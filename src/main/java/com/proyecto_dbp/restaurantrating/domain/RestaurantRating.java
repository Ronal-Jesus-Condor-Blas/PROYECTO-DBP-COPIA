package com.proyecto_dbp.restaurantrating.domain;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RestaurantRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantRatingId;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)  // Clave foránea hacia Restaurant
    private Restaurant restaurant;  // Agrega esta línea

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;


    private LocalDateTime ratingDate;
}