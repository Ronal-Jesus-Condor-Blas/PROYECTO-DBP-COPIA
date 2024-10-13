package com.proyecto_dbp.food.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.proyecto_dbp.exception.ValidationException;

import java.io.IOException;

public class FoodStatusDeserializer extends JsonDeserializer<FoodStatus> {

    @Override
    public FoodStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();
        try {
            return FoodStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid status value: " + value);
        }
    }
}