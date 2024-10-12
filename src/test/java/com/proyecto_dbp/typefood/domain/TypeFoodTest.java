package com.proyecto_dbp.typefood.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypeFoodTest {
    private TypeFood typeFood;

    @BeforeEach
    public void setUp() {
        typeFood = new TypeFood();
        typeFood.setTypeFoodId(1L);
        typeFood.setType("Italian");
    }

    @Test
    public void testTypeFoodCreation() {
        assertNotNull(typeFood);
        assertEquals(1L, typeFood.getTypeFoodId());
        assertEquals("Italian", typeFood.getType());
    }

    @Test
    public void testSetType() {
        typeFood.setType("Mexican");
        assertEquals("Mexican", typeFood.getType());
    }
}