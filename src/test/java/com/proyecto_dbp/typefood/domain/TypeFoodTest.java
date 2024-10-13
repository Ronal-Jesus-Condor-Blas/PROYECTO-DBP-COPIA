package com.proyecto_dbp.typefood.domain;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TypeFoodTest {
    private TypeFood typeFood;
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setName("Test Restaurant");

        typeFood = new TypeFood();
        typeFood.setTypeFoodId(1L);
        typeFood.setType("Italian");
        typeFood.setDescription("Traditional Italian cuisine");
        typeFood.setRestaurant(restaurant);
    }

    @Test
    public void testTypeFoodCreation() {
        assertNotNull(typeFood);
        assertEquals(1L, typeFood.getTypeFoodId());
        assertEquals("Italian", typeFood.getType());
        assertEquals("Traditional Italian cuisine", typeFood.getDescription());
        assertEquals(restaurant, typeFood.getRestaurant());
    }

    @Test
    public void testSetType() {
        typeFood.setType("Mexican");
        assertEquals("Mexican", typeFood.getType());
    }

    @Test
    public void testSetDescription() {
        typeFood.setDescription("Traditional Mexican cuisine");
        assertEquals("Traditional Mexican cuisine", typeFood.getDescription());
    }

    @Test
    public void testSetRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setRestaurantId(2L);
        newRestaurant.setName("New Test Restaurant");
        typeFood.setRestaurant(newRestaurant);
        assertEquals(newRestaurant, typeFood.getRestaurant());
    }
}