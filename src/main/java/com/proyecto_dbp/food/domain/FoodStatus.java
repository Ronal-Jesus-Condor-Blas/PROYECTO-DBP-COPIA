package com.proyecto_dbp.food.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//@JsonDeserialize(using = FoodStatusDeserializer.class)
public enum FoodStatus {
    AVAILABLE,
    UNAVAILABLE
}