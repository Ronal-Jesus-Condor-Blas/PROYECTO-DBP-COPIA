//OK
package com.proyecto_dbp.auth.dto;

import com.proyecto_dbp.user.dto.UserResponseDto;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private UserResponseDto user;
}
