package com.proyecto_dbp.food.domain;

import com.proyecto_dbp.foodrating.domain.FoodRating;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FoodTest {
    private Food food;
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setLocation("123 Test St");

        food = new Food();
        food.setFoodId(1L);
        food.setName("Test Food");
        food.setPrice(10.99);
        food.setAverageRating(4.5);
        food.setCreatedDate(LocalDateTime.now());
        food.setStatus(FoodStatus.AVAILABLE);
        food.setRestaurant(restaurant);

        FoodRating rating1 = new FoodRating();
        rating1.setFoodRatingId(1L);
        rating1.setRating(4);
        rating1.setComment("Tasty!");
        rating1.setFood(food);

        FoodRating rating2 = new FoodRating();
        rating2.setFoodRatingId(2L);
        rating2.setRating(5);
        rating2.setComment("Excellent!");
        rating2.setFood(food);

        List<FoodRating> foodRatings = new ArrayList<>();
        foodRatings.add(rating1);
        foodRatings.add(rating2);
        food.setFoodRatings(foodRatings);
    }

    @Test
    public void testFoodCreation() {
        assertNotNull(food);
        assertEquals(1L, food.getFoodId());
        assertEquals("Test Food", food.getName());
        assertEquals(10.99, food.getPrice());
        assertEquals(4.5, food.getAverageRating());
        assertEquals(LocalDateTime.now().getDayOfWeek(), food.getCreatedDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), food.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), food.getCreatedDate().getMonth());
        assertEquals(FoodStatus.AVAILABLE, food.getStatus());
        assertEquals(restaurant, food.getRestaurant());
        assertEquals(2, food.getFoodRatings().size());
    }

    @Test
    public void testSetRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setRestaurantId(2L);
        newRestaurant.setName("New Restaurant");
        newRestaurant.setLocation("456 New St");

        food.setRestaurant(newRestaurant);
        assertEquals(newRestaurant, food.getRestaurant());
    }

    @Test
    public void testAddFoodRating() {
        FoodRating rating3 = new FoodRating();
        rating3.setFoodRatingId(3L);
        rating3.setRating(3);
        rating3.setComment("Good, but could be better.");
        rating3.setFood(food);

        List<FoodRating> foodRatings = new ArrayList<>(food.getFoodRatings());
        foodRatings.add(rating3);
        food.setFoodRatings(foodRatings);

        assertEquals(3, food.getFoodRatings().size());
    }

    @Test
    public void testRemoveFoodRating() {
        List<FoodRating> foodRatings = new ArrayList<>(food.getFoodRatings());
        foodRatings.removeIf(rating -> rating.getFoodRatingId().equals(1L));
        food.setFoodRatings(foodRatings);

        assertEquals(1, food.getFoodRatings().size());
    }
}