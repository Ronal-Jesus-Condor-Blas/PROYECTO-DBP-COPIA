package com.proyecto_dbp.user.event;

import com.proyecto_dbp.user.domain.User;
import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private final User user;

    public UserRegisteredEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}