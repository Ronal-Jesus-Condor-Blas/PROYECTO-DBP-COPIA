package com.proyecto_dbp.auth.application;

import com.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.proyecto_dbp.auth.dto.LoginRequest;
import com.proyecto_dbp.auth.dto.RegisterRequest;
import com.proyecto_dbp.auth.domain.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        JwtAuthResponse jwtAuthResponse = authService.register(registerRequest);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}