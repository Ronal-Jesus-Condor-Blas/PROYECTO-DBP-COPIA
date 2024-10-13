package com.proyecto_dbp.typefood.domain;


import com.proyecto_dbp.email.NewRestaurantEvent;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class TypeFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeFoodId;

    @NotNull
    @Size(max = 100)
    private String type; // Tipo de comida (e.g. italiana, mexicana, peruana)

    private String description; // Descripción del tipo de comida

    @ManyToMany(mappedBy = "typesOfFood")
    private Set<Restaurant> restaurants; // Relación con Restaurant


    public String getType() {
        return type;
    }



    // Getters and Setters
    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
