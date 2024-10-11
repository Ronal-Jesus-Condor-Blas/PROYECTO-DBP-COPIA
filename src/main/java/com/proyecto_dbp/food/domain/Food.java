package com.proyecto_dbp.food.domain;

import com.proyecto_dbp.foodrating.domain.FoodRating;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data  // Lombok genera automáticamente getters, setters, toString, equals, y hashCode
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @NotNull
    @Size(max = 100)
    private String name;

    private Double price;

    private Double averageRating;

    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private FoodStatus status;  // Enum para el estado del plato (disponible/no disponible)

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)  // Clave foránea hacia Restaurant
    private Restaurant restaurant;  // Un plato pertenece a un restaurante

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodRating> foodRatings;  // Un plato puede tener muchas calificaciones
}