package com.proyecto_dbp.auth.domain;

import com.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.proyecto_dbp.auth.dto.LoginRequest;
import com.proyecto_dbp.auth.dto.RegisterRequest;
import com.proyecto_dbp.exception.UserAlreadyExistException;
import com.proyecto_dbp.auth.utils.JwtTokenProvider;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        String token = tokenProvider.generateToken(authentication);
        return new JwtAuthResponse(token);
    }

    public JwtAuthResponse register(RegisterRequest registerRequest) {
        if (userService.getUserByEmail(registerRequest.getEmail()) != null) {
            throw new UserAlreadyExistException("User with email " + registerRequest.getEmail() + " already exists");
        }

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(registerRequest.getName());
        userRequestDto.setEmail(registerRequest.getEmail());
        userRequestDto.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRequestDto.setBio(registerRequest.getBio());
        userRequestDto.setUserType(registerRequest.getUserType());

        userService.createUser(userRequestDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );

        String token = tokenProvider.generateToken(authentication);
        return new JwtAuthResponse(token);
    }
}