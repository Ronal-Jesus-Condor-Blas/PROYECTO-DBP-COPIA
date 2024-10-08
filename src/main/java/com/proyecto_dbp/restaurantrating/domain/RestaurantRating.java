package com.proyecto_dbp.restaurantrating.domain;

import com.proyecto_dbp.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data  // Lombok generará automáticamente los getters, setters, toString, equals, y hashCode
public class RestaurantRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantRatingId;

    @ManyToOne
    private User user;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;  // Calificación de 1 a 5 estrellas

    private String comment;  // Comentario opcional

    private LocalDateTime ratingDate;  // Fecha en la que se realizó la calificación

}