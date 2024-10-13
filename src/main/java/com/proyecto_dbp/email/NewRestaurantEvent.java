package com.proyecto_dbp.email;

import org.springframework.context.ApplicationEvent;

public class NewRestaurantEvent extends ApplicationEvent {

    private final String restaurantId;
    private final String email;
    private final String name;
    private final String Location;

    public NewRestaurantEvent(String restaurantId, String email, String name, String Location) {
        super(restaurantId);
        this.restaurantId = restaurantId;
        this.email = email;
        this.name = name;
        this.Location = Location;

    }

    public String getEmail() {
        return email;
    }

    public Object getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return Location;
    }
}
