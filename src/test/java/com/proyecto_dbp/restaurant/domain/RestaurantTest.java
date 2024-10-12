package com.proyecto_dbp.restaurant.domain;

import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import com.proyecto_dbp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestaurantTest {
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setLocation("123 Test St");
        restaurant.setCreatedDate(LocalDateTime.now());

        RestaurantRating rating1 = new RestaurantRating();
        rating1.setRestaurantRatingId(1L);
        rating1.setRating(4);
        rating1.setComment("Great food!");
        rating1.setRestaurant(restaurant);

        RestaurantRating rating2 = new RestaurantRating();
        rating2.setRestaurantRatingId(2L);
        rating2.setRating(5);
        rating2.setComment("Excellent service!");
        rating2.setRestaurant(restaurant);

        Set<RestaurantRating> ratings = new HashSet<>();
        ratings.add(rating1);
        ratings.add(rating2);
        restaurant.setRatings(ratings);
    }

    @Test
    public void testRestaurantCreation() {
        assertNotNull(restaurant);
        assertEquals(1L, restaurant.getRestaurantId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test St", restaurant.getLocation());
        assertEquals(LocalDateTime.now().getDayOfWeek(), restaurant.getCreatedDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), restaurant.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), restaurant.getCreatedDate().getMonth());
        assertEquals(2, restaurant.getRatings().size());
    }

    @Test
    public void testAddRating() {
        RestaurantRating rating3 = new RestaurantRating();
        rating3.setRestaurantRatingId(3L);
        rating3.setRating(3);
        rating3.setComment("Good, but could be better.");
        rating3.setRestaurant(restaurant);
        Set<RestaurantRating> ratings = new HashSet<>(restaurant.getRatings());
        ratings.add(rating3);
        restaurant.setRatings(ratings);
        assertEquals(3, restaurant.getRatings().size());
    }

    @Test
    public void testRemoveRating() {
        Set<RestaurantRating> ratings = new HashSet<>(restaurant.getRatings());
        ratings.removeIf(rating -> rating.getRestaurantRatingId().equals(1L));
        restaurant.setRatings(ratings);
        assertEquals(1, restaurant.getRatings().size());
    }
}