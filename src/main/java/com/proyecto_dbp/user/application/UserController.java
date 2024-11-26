package com.proyecto_dbp.user.application;

import com.proyecto_dbp.config.JwtService;
import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private JwtService jwtService;

    //pata mandar correos usando eventos
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        if (userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDto> createUser(
            @RequestPart("user") UserRequestDto userRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        UserResponseDto createdUser = userService.createUser(userRequestDto, image);

        // Lanzar evento para enviar correo de bienvenida
        applicationEventPublisher.publishEvent(new HelloEmailEvent(
                createdUser.getUserId(),
                createdUser.getEmail(),
                createdUser.getName(),
                createdUser.getUserType()
        ));

        return ResponseEntity.ok(createdUser);
    }

    //Code for patch mapping
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserPartial(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUserPartial(id, userRequestDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id, @RequestPart("user") UserRequestDto userRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto, image);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



}