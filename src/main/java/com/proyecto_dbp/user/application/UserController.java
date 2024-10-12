package com.proyecto_dbp.user.application;

import com.proyecto_dbp.auth.utils.AuthorizationUtils;
import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserDto;
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
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserDto userDto = userService.getUserById(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserDto updatedUser = userService.updateUser(id, userDto);
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
    public ResponseEntity<UserDto> getCurrentUser() {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserDto userDto = userService.getUserByEmail(email);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }
}