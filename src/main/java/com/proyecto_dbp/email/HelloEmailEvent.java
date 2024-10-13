package com.proyecto_dbp.email;

import com.proyecto_dbp.user.domain.UserType;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.context.ApplicationEvent;

public class HelloEmailEvent extends ApplicationEvent {

    private final Long userId;
    private final String email;
    private final String nombre;
    private final String rol;

    public HelloEmailEvent(Long userId, String email, String nombre, UserType rol) {
        super(userId);
        this.userId = userId;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol.toString();
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}
