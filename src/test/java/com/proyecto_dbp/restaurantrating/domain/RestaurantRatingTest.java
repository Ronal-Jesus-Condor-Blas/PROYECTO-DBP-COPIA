package com.proyecto_dbp.restaurantrating.domain;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestaurantRatingTest {
    private RestaurantRating restaurantRating;
    private Restaurant restaurant;
    private User user;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setName("Test Restaurant");

        user = new User();
        user.setUserId(1L);
        user.setEmail("testuser@example.com");

        restaurantRating = new RestaurantRating();
        restaurantRating.setRestaurantRatingId(1L);
        restaurantRating.setRestaurant(restaurant);
        restaurantRating.setUser(user);
        restaurantRating.setRating(5);
        restaurantRating.setComment("Excellent!");
        restaurantRating.setRatingDate(LocalDateTime.now());
    }

    @Test
    public void testRestaurantRatingCreation() {
        assertNotNull(restaurantRating);
        assertEquals(1L, restaurantRating.getRestaurantRatingId());
        assertEquals(restaurant, restaurantRating.getRestaurant());
        assertEquals(user, restaurantRating.getUser());
        assertEquals(5, restaurantRating.getRating());
        assertEquals("Excellent!", restaurantRating.getComment());
        assertEquals(LocalDateTime.now().getDayOfWeek(), restaurantRating.getRatingDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), restaurantRating.getRatingDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), restaurantRating.getRatingDate().getMonth());
    }

    @Test
    public void testSetComment() {
        restaurantRating.setComment("Very good");
        assertEquals("Very good", restaurantRating.getComment());
    }

    @Test
    public void testSetRating() {
        restaurantRating.setRating(4);
        assertEquals(4, restaurantRating.getRating());
    }
}