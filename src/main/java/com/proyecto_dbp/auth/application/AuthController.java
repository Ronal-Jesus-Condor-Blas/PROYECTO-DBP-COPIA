//OK
package com.proyecto_dbp.auth.application;

import com.proyecto_dbp.auth.domain.AuthService;
import com.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.proyecto_dbp.auth.dto.LoginReq;
import com.proyecto_dbp.auth.dto.RegisterReq;
import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.user.domain.UserType;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthController{

    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq req){
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("bio") String bio,
            @RequestParam("userType") String userType,
            @RequestParam("password") String password,
            @RequestParam("profilePicture") MultipartFile profilePicture) {

        RegisterReq req = new RegisterReq();
        req.setName(name);
        req.setEmail(email);
        req.setBio(bio);
        req.setUserType(UserType.valueOf(userType));  // Asegúrate de que 'userType' esté mapeado correctamente
        req.setPassword(password);
        req.setProfilePicture(profilePicture);

        JwtAuthResponse response = authService.register(req);

        return ResponseEntity.ok(response);
    }
}
