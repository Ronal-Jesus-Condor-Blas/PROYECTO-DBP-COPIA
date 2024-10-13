package com.proyecto_dbp.restaurantrating.infrastructure;

import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, Long> {
    List<RestaurantRating> findByRestaurantRestaurantId(Long restaurantId);
    List<RestaurantRating> findByUserUserId(Long userId);
}