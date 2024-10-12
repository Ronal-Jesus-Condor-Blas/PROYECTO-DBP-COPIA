package com.proyecto_dbp.foodrating.domain;

import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FoodRatingTest {
    private FoodRating foodRating;
    private Food food;
    private User user;

    @BeforeEach
    public void setUp() {
        food = new Food();
        food.setFoodId(1L);
        food.setName("Test Food");

        user = new User();
        user.setUserId(1L);
        user.setEmail("testuser@example.com");

        foodRating = new FoodRating();
        foodRating.setFoodRatingId(1L);
        foodRating.setFood(food);
        foodRating.setUser(user);
        foodRating.setComment("Delicious!");
        foodRating.setRating(5);
        foodRating.setRatingDate(LocalDateTime.now());
    }

    @Test
    public void testFoodRatingCreation() {
        assertNotNull(foodRating);
        assertEquals(1L, foodRating.getFoodRatingId());
        assertEquals(food, foodRating.getFood());
        assertEquals(user, foodRating.getUser());
        assertEquals("Delicious!", foodRating.getComment());
        assertEquals(5, foodRating.getRating());
        assertEquals(LocalDateTime.now().getDayOfWeek(), foodRating.getRatingDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), foodRating.getRatingDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), foodRating.getRatingDate().getMonth());
    }

    @Test
    public void testSetComment() {
        foodRating.setComment("Not bad");
        assertEquals("Not bad", foodRating.getComment());
    }

    @Test
    public void testSetRating() {
        foodRating.setRating(4);
        assertEquals(4, foodRating.getRating());
    }
}