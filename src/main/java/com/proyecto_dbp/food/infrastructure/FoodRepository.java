package com.proyecto_dbp.food.infrastructure;

import com.proyecto_dbp.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurantRestaurantId(Long restaurantId);
}