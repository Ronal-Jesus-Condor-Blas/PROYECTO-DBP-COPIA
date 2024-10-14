package com.proyecto_dbp.auth.domain;

import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.user.domain.Role;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.proyecto_dbp.auth.dto.LoginReq;
import com.proyecto_dbp.auth.dto.RegisterReq;
import com.proyecto_dbp.auth.exceptions.UserAlreadyExistException;
import com.proyecto_dbp.config.JwtService;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;


@Service
public class AuthService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user;
        user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email already in use");

        User newUser = modelMapper.map(req, User.class);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setCreatedAt(ZonedDateTime.now());
        newUser.setUpdatedAt(ZonedDateTime.now());
        newUser.setRole(Role.USER);
        //newUser.setUserType(req.getUserType());

        userRepository.save(newUser);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(newUser));

        //adicional
        applicationEventPublisher.publishEvent(new HelloEmailEvent(newUser.getUserId(), newUser.getEmail(), newUser.getName(), newUser.getUserType())); //email

        return response;
    }


}