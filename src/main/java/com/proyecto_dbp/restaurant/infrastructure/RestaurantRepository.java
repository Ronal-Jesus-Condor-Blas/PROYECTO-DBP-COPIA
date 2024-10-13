package com.proyecto_dbp.restaurant.infrastructure;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Object> findByEmail(String email);
}