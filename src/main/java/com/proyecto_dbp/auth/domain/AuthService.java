package com.proyecto_dbp.auth.domain;

import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.user.domain.Role;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.proyecto_dbp.auth.dto.LoginReq;
import com.proyecto_dbp.auth.dto.RegisterReq;
import com.proyecto_dbp.auth.exceptions.UserAlreadyExistException;
import com.proyecto_dbp.config.JwtService;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.domain.UserType;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    private UserService userService;


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
        Optional<User> userOptional = userRepository.findByEmail(req.getEmail());

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Email is not registered");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(user));

        // Convertir User a UserResponseDto (asumiendo que tienes un método de mapeo en UserService o un mapper)
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        response.setUser(userDto);

        return response;
    }

    public JwtAuthResponse register(RegisterReq req) {
        // Verificar si el usuario ya existe
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistException("Email already in use");
        }

        // Usamos el UserService para crear el usuario y manejar la imagen de perfil
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(req.getName());
        userRequestDto.setEmail(req.getEmail());
        userRequestDto.setBio(req.getBio());
        userRequestDto.setUserType(UserType.valueOf(req.getUserType().name()));  // Suponiendo que el UserType es un enum
        userRequestDto.setPassword(passwordEncoder.encode(req.getPassword()));

        // Llamamos al servicio de usuario para crear el usuario con la imagen
        UserResponseDto userResponseDto = userService.createUser(userRequestDto, req.getProfilePicture());

        // Crear la respuesta JWT
        JwtAuthResponse response = new JwtAuthResponse();

        // Aquí cambiamos a usar el objeto User en lugar de UserResponseDto
        User userEntity = userRepository.findById(userResponseDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //set role user
        userEntity.setRole(Role.USER);

        response.setToken(jwtService.generateToken(userEntity));

        response.setUser(userResponseDto);

        // Publicar el evento de correo de bienvenida
        applicationEventPublisher.publishEvent(new HelloEmailEvent(
                userResponseDto.getUserId(),
                userResponseDto.getEmail(),
                userResponseDto.getName(),
                userResponseDto.getUserType()
        ));

        return response;
    }
}