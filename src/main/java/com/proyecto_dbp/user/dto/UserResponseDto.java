package com.proyecto_dbp.user.dto;

import com.proyecto_dbp.user.domain.UserType;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String bio;
    private UserType userType;
    private String profilePicture;
}