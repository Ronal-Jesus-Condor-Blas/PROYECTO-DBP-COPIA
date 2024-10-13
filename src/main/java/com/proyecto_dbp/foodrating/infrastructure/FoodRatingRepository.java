package com.proyecto_dbp.foodrating.infrastructure;

import com.proyecto_dbp.foodrating.domain.FoodRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRatingRepository extends JpaRepository<FoodRating, Long> {
    List<FoodRating> findByFoodFoodId(Long foodId);  // Correct method name

    // Remove the incorrect method
    // List<FoodRating> findByFoodId(Long foodId);
}