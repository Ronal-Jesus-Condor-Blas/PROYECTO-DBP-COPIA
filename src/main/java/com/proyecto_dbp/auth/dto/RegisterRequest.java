package com.proyecto_dbp.auth.dto;

import com.proyecto_dbp.user.domain.UserType;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String bio;
    private UserType userType;
}