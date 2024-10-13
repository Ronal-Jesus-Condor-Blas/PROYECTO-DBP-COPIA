package com.proyecto_dbp.user.application;

import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        //lanzar evento "mandar un correo de bienvenida"
        //crea el codigo para mandar el correo

        UserResponseDto createdUser = userService.createUser(userRequestDto);
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

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.status(403).build(); // Forbidden
    }

    //Post for email
    @PostMapping("/email")
    public ResponseEntity<UserResponseDto> emailcreateUser(@RequestBody UserRequestDto userRequestDto) {
        //lanzar evento "mandar un correo de bienvenida"
        //crea el codigo para mandar el correo

        //crear usuario OK
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        //lanzar evento "mandar un correo de bienvenida"
        applicationEventPublisher.publishEvent(new HelloEmailEvent(createdUser.getUserId(), createdUser.getEmail(), createdUser.getName(), createdUser.getUserType())); //email
        return ResponseEntity.ok(createdUser);
    }
}