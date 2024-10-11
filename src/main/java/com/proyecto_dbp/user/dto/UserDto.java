package com.proyecto_dbp.user.dto;

import com.proyecto_dbp.user.domain.UserType;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private String bio;
    private UserType userType;
}