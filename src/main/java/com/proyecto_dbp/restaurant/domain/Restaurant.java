package com.proyecto_dbp.restaurant.domain;

import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import com.proyecto_dbp.typefood.domain.TypeFood;
import com.proyecto_dbp.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Data  // Lombok generará automáticamente los getters, setters, toString, equals, y hashCode
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    //agregar email apra recibir confirmaciones de "foodrating"

    @NotNull
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private String location;

    private Double averageRating;
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;  // Enum para el estado del restaurante (abierto/cerrado)

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Food> foods;  // Un restaurante ofrece varios platos

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<RestaurantRating> ratings;  // Un restaurante puede recibir varias calificaciones

    @ManyToMany
    @JoinTable(
            name = "restaurant_type_food",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "type_food_id")
    )
    private Set<TypeFood> typesOfFood;  // Un restaurante ofrece varios tipos de cocina



}
