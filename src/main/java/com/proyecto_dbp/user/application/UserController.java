package com.proyecto_dbp.user.application;

import com.proyecto_dbp.auth.utils.AuthorizationUtils;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserResponseDto userResponseDto = userService.getUserById(id);
        if (userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserResponseDto userResponseDto = userService.getUserByEmail(email);
        if (userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        }
        return ResponseEntity.notFound().build();
    }
}