package com.proyecto_dbp.restaurant.domain;

/*
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import com.proyecto_dbp.typefood.domain.TypeFood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setLocation("123 Test St");
        restaurant.setEmail("testrestaurant@example.com");
        restaurant.setAverageRating(4.5);
        restaurant.setCreatedDate(LocalDateTime.now());
        restaurant.setStatus(RestaurantStatus.OPEN);

        Food food1 = new Food();
        food1.setFoodId(1L);
        food1.setName("Test Food 1");
        food1.setPrice(10.99);
        food1.setRestaurant(restaurant);

        Food food2 = new Food();
        food2.setFoodId(2L);
        food2.setName("Test Food 2");
        food2.setPrice(12.99);
        food2.setRestaurant(restaurant);

        Set<Food> foods = new HashSet<>();
        foods.add(food1);
        foods.add(food2);
        restaurant.setFoods(foods);

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

        TypeFood typeFood1 = new TypeFood();
        typeFood1.setTypeFoodId(1L);
        typeFood1.setType("Italian");

        TypeFood typeFood2 = new TypeFood();
        typeFood2.setTypeFoodId(2L);
        typeFood2.setType("Mexican");

        Set<TypeFood> typesOfFood = new HashSet<>();
        typesOfFood.add(typeFood1);
        typesOfFood.add(typeFood2);
        restaurant.setTypesOfFood(typesOfFood);
    }

    @Test
    public void testRestaurantCreation() {
        assertNotNull(restaurant);
        assertEquals(1L, restaurant.getRestaurantId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test St", restaurant.getLocation());
        assertEquals("testrestaurant@example.com", restaurant.getEmail());
        assertEquals(4.5, restaurant.getAverageRating());
        assertEquals(LocalDateTime.now().getDayOfWeek(), restaurant.getCreatedDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), restaurant.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), restaurant.getCreatedDate().getMonth());
        assertEquals(RestaurantStatus.OPEN, restaurant.getStatus());
        assertEquals(2, restaurant.getFoods().size());
        assertEquals(2, restaurant.getRatings().size());
        assertEquals(2, restaurant.getTypesOfFood().size());
    }

    @Test
    public void testAddFood() {
        Food food3 = new Food();
        food3.setFoodId(3L);
        food3.setName("Test Food 3");
        food3.setPrice(15.99);
        food3.setRestaurant(restaurant);

        Set<Food> foods = new HashSet<>(restaurant.getFoods());
        foods.add(food3);
        restaurant.setFoods(foods);

        assertEquals(3, restaurant.getFoods().size());
    }

    @Test
    public void testRemoveFood() {
        Set<Food> foods = new HashSet<>(restaurant.getFoods());
        foods.removeIf(food -> food.getFoodId().equals(1L));
        restaurant.setFoods(foods);

        assertEquals(1, restaurant.getFoods().size());
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

    @Test
    public void testUpdateRestaurantEmail() {
        restaurant.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", restaurant.getEmail());
    }

    @Test
    public void testUpdateRestaurantStatus() {
        restaurant.setStatus(RestaurantStatus.CLOSED);
        assertEquals(RestaurantStatus.CLOSED, restaurant.getStatus());
    }
}

 */